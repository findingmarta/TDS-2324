import { Platform } from 'react-native';
import {Component} from 'react';
import VIForegroundService from '@voximplant/react-native-foreground-service';
import Geolocation from 'react-native-geolocation-service';


class LocationTracker extends Component {
    // Props includes the points of interest and the user's preferences
    constructor(props) {
        super(props);
        this.state = {
            isRunningService: false,
        };
        this.foregroundService = VIForegroundService.getInstance();
        this.locationWatchId = null;
        this.lastLocation = null;
        this.travelledDistance = 0;
        this.notificationCounter = 1235;
        this.point = null;
    }

    async startService() {
        if (Platform.OS !== 'android') {
            console.log('Only Android platform is supported');
            return;
        }
        
        if (this.state.isRunningService) return;

        if (Platform.Version >= 26) {
            const channelConfig = {
                id: 'LocationServiceChannel',
                name: 'Notification Channel',
                description: 'Notification Channel for Foreground Service',
                enableVibration: true,
                importance: 3
            };
            await this.foregroundService.createNotificationChannel(channelConfig);
        }
        
        const notificationConfig = {
            channelId: 'LocationServiceChannel',
            id: 1234,
            title: 'Foreground Service',
            text: 'Foreground service is running',
            icon: 'ic_launcher_foreground',
            priority: 2,
        };

        try {
            this.subscribeForegroundButtonPressedEvent();
            await this.foregroundService.startService(notificationConfig);
            this.setState({isRunningService: true});
            this.startLocationUpdates();
        } catch (error) {
            console.error('Failed to start foreground service', error);
            this.foregroundService.off();
        }
    }

    async stopService() {
        this.travelledDistanceCallback();
        if (!this.state.isRunningService) return;
        this.setState({isRunningService: false});
        await this.foregroundService.stopService();
        this.foregroundService.off();
        if (this.locationWatchId !== null) {
            Geolocation.clearWatch(this.locationWatchId);
            this.locationWatchId = null;
        }
    }

    subscribeForegroundButtonPressedEvent() {
        this.foregroundService.on('VIForegroundServiceButtonPressed', async () => {
            const { navigation } = this.props;
            navigation.navigate('PointPage',{point: this.point},navigation);
        });
    }

    // travelledDistance callback function
    travelledDistanceCallback = () => {
        if (this.props.onTravelledDistanceUpdate) {
            this.props.onTravelledDistanceUpdate(this.travelledDistance);
        }
    }

    startLocationUpdates() {
        this.locationWatchId = Geolocation.watchPosition(
            (position) => {

                // Compute the distance travelled      
                if (this.lastLocation !== null) {
                    const distance = this.computeDistance(this.lastLocation, position);
                    this.travelledDistance += distance;
                }
                console.info("\n")
                console.info(`Travelled distance: ${this.travelledDistance}`);

                // Check proximity to points of interest
                this.checkProximity(position);

                // Update the location
                this.lastLocation = position;
            },
            (error) => {
                console.error(error);
            },
            {
                enableHighAccuracy: true,
                distanceFilter: 100,        // 100 meters
                interval: 30000,            // 30 seconds
                fastestInterval: 30000,
            }
        );
    }

    computeDistance(oldLocation,newLocation) {
        const earthRadius = 6378137;
        const toRadians = (value) => (value * Math.PI) / 180;
        
        const oldLat = toRadians(oldLocation.coords.latitude);
        const oldLon = toRadians(oldLocation.coords.longitude);
        const newLat = toRadians(newLocation.coords.latitude);
        const newLon = toRadians(newLocation.coords.longitude);
        
        const cosineDistance = 
                        Math.sin(newLat) * Math.sin(oldLat) +
                        Math.cos(newLat) * Math.cos(oldLat) * Math.cos(newLon - oldLon);

        // Clamp the value to the range [-1, 1] to avoid NaN due to floating-point errors
        const clampedCosineDistance = Math.min(Math.max(cosineDistance, -1), 1);

        const distance = Math.acos(clampedCosineDistance) * earthRadius;
          
        return distance;
    }

    checkProximity(currentPosition) {
        const points = this.props.points;
        const {notifications, notify_distance} = this.props.preferences;
        const points_history = this.props.points_history;
        
        // Debugging
        console.info("Notifications On: ", notifications)
        console.info( `Preferences: ${notify_distance} meters.`)
        console.info("\n")

        if (notifications){
            points.forEach(point => {
                const distance = this.computeDistance(currentPosition, {
                    coords: { latitude: point.pin_lat, longitude: point.pin_lng }
                });
                
                // check if the point was already visited
                const visited = points_history.some(p => p.id === point.id);
                if (!visited){
                    console.info(`Distance to ${point.pin_name}: ${Math.trunc(distance)} meters`);
                    
                    if (distance <= notify_distance) {
                        this.point = point;
                        this.sendNotification(`Heads Up!`,`You are ${notify_distance} meters away from ${point.pin_name}.`);
                    }
                }
            });
        }
    }

    async sendNotification(title,text) {
        try {
            const notificationConfig = {
                channelId: 'LocationServiceChannel',
                id: ++this.notificationCounter,
                title: title,
                text: text,
                icon: 'ic_launcher_foreground',
                priority: 2,
                button: 'Visit Point of Interest'
            };
            await this.foregroundService.startService(notificationConfig);            
        } catch (error) {
            console.error("Failed to send notification", error);
        }
    }

    render() {
        return null;
    }
}

export default LocationTracker;