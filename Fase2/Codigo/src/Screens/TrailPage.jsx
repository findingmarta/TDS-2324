import React, { useEffect, useState, useRef  } from 'react';
import { useDispatch,useSelector } from "react-redux"
import {Image, StyleSheet, Text, View, TouchableOpacity, Linking, Alert, PermissionsAndroid, Platform} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { useNavigation } from '@react-navigation/native';
import MapView, {Marker, Polyline} from 'react-native-maps';
import Geolocation from 'react-native-geolocation-service';

import PointsItem from '../Components/PointsItem';
import { addTrail, updateTrail } from '../features/historySlice';
import { updateTrailRunning } from '../features/userSlice';
import LocationTracker from './Services/LocationTracker';

import { COLORS } from '../style/colors';

function getTrailPoints (edges) {
    const points = [];
    const ids = [];
  
    edges.map((edge) => {
        const start = edge.edge_start;
        const end = edge.edge_end; 
  
        if (!ids.includes(start.id)){
            points.push(start);
            ids.push(start.id);
        }
  
  
        if (!ids.includes(end.id)){
          points.push(end);
          ids.push(end.id);
        }
    })
  
    return points;
}

function openGoogleMaps (trail_points, initital_location) {
    const url = "https://www.google.com/maps/dir/";
    const origin = `${initital_location.latitude},${initital_location.longitude}`;
    let destination =  ""
    trail_points.map((point) => {
        destination += `/${point.pin_lat},${point.pin_lng}`
    })
    const link = url + origin + destination;
    Linking.openURL(link);
}

async function requestLocationPermission() {

    if (Platform.OS === 'ios') {
        Geolocation.setRNConfiguration({authorizationLevel: 'whenInUse',});
        Geolocation.requestAuthorization();
        // IOS permission request does not offer a callback
        return null;
    }

    if (Platform.OS === 'android') {
        try {
            const fineLocationGranted = await PermissionsAndroid.request(
                PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
            );
  
            if (fineLocationGranted === PermissionsAndroid.RESULTS.GRANTED) {
                let backgroundLocationGranted = true;
                if (Platform.Version >= 29) {
                    backgroundLocationGranted = await PermissionsAndroid.request(
                        PermissionsAndroid.PERMISSIONS.ACCESS_BACKGROUND_LOCATION
                    );
                }
  
                if (backgroundLocationGranted === PermissionsAndroid.RESULTS.GRANTED) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (err) {
            console.warn(err.message);
            return false;
        }
    }
}

async function getInitialLocation() {
    return new Promise((resolve, reject) => {
        //const hasLocationPermission = await requestLocationPermission();

        if (hasLocationPermission){
            Geolocation.getCurrentPosition(
                (position) => {
                    console.log("INITIAL COORDS: ", position.coords.latitude, " ", position.coords.longitude);
                    resolve(position.coords);
                },
                (error) => {
                    console.log(error.code, error.message);
                    resolve(null);
                },
                { enableHighAccuracy: true, timeout: 15000, maximumAge: 10000 }
            );
        } else {
            resolve(null);
        }
    });
} 

let start_time = 0;
let stop_time = 0;
let travelled_time = 0;
let hasLocationPermission = false;
let start_date = "";

function TrailPage ({route}) {
    const dispatch = useDispatch();
    const navigation = useNavigation();

    // Get Initial States
    const isPremium = useSelector((state) => state.user.isPremium);
    const preferences = useSelector((state) => state.user.preferences);
    const trail_running = useSelector((state) => state.user.trail_running);
    const trails_history = useSelector((state) => state.history.trails);
    const points_history = useSelector((state) => state.history.points);

    const trail = route.params.trail;
    const trail_points = getTrailPoints(trail.edges);

    // Get permissions for premium users
    useEffect(() => {
         if (isPremium){
            requestLocationPermission();
            hasLocationPermission = true;
         }
    }, []);

    const initialRegion = {
        latitude: trail_points[0].pin_lat,
        longitude: trail_points[0].pin_lng,
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
    };

    const polyCoords = trail_points.map((point) => ({
        latitude: point.pin_lat,
        longitude: point.pin_lng,
    }));

    const isThisTrailRunning = trail.id === trail_running;

    const [startDisable, setStartPressed] = useState(isThisTrailRunning || trail_running !== -1);   
    const [stopDisable, setStopPressed] = useState(!isThisTrailRunning || trail_running === -1);    
    const [travelled_distance, setTravelledDistance] = useState(null);
    
    const locationTrackerRef = useRef(null);

    // Function to update travelledDistance state
    const updateTravelledDistance = (distance) => {
        if (distance !== travelled_distance) {
            setTravelledDistance(Math.trunc(distance));
        }
    }

    // Update the history when the trail is updated
    useEffect(() => {
        updateHistory();
    }, [travelled_distance]);

    // Function to update the history
    const updateHistory = () => {
        if (travelled_distance === null) return;

        // Add trail to history
        newTrail = {
            id: trail.id,
            name: trail.trail_name,
            date: start_date,
            duration: trail.trail_duration,
            difficulty: trail.trail_difficulty,
            travelled_distance: travelled_distance,
            travelled_time: travelled_time,
        }

        // Check if trail is already in history
        const exists = trails_history.some(t => t.id === newTrail.id);
        if (!exists) {
            dispatch(addTrail(newTrail));
            Alert.alert('Trail added to history!');
        } else {
            dispatch(updateTrail(newTrail));
            Alert.alert('Trail updated in history!');
        }
        setTravelledDistance(null)
    }


    const handleStart = async () => {
        start_time = new Date().getTime();
    
        setStartPressed(true);
        setStopPressed(false);
    
        location = await getInitialLocation();
    
        if (location){
            // Start location tracking
            locationTrackerRef.current.startService();

            // Update the trail running state
            dispatch(updateTrailRunning(trail.id));

            // Open Google Maps
            openGoogleMaps(trail_points, location);  
        }    
    }
    
    const handleStop = () =>{
        stop_time = new Date().getTime();
        travelled_time = Math.floor((stop_time - start_time) / 60000);
        
        const start_date_raw = new Date(start_time);
        start_date = `${start_date_raw.getDate()}-${start_date_raw.getMonth() + 1}-${start_date_raw.getFullYear()}`;
    
        setStartPressed(false);
        setStopPressed(true);

        // Reset the trail running state
        dispatch(updateTrailRunning(-1));

        // Stop location tracking
        locationTrackerRef.current.stopService();
    }
    
    return (
        <View style={styles.container}>
            <ScrollView showsVerticalScrollIndicator={false}>

                <View style={styles.trail_container}>
                    <View style={styles.title_container}>
                        <Text style={styles.trail_name}>{trail.trail_name}</Text>
                    </View>

                    <View style={styles.text_container}>
                        <Text style={styles.trail_details}>{trail.trail_duration}</Text>
                        <Text style={styles.trail_details}>{trail.trail_difficulty}</Text>
                    </View>
                </View>

                <Image style={styles.trail_image} source={{uri: trail.trail_img}}/>

                <Text style={styles.title}>DESCRIPTION</Text>
                <Text style={styles.text}>{trail.trail_desc}</Text>


                <Text style={styles.title}>ITINERARY</Text>

                {isPremium && (
                    <View>
                        <MapView style={styles.map_container} initialRegion={initialRegion}>
                            <Polyline coordinates={polyCoords} strokeColor={COLORS.logo_blue} strokeWidth={5} />
                            {trail_points.map((marker) => (
                                <Marker
                                    key={marker.id}
                                    coordinate={{latitude: marker.pin_lat, longitude: marker.pin_lng}}
                                    title={marker.pin_name}
                                />
                            ))}
                        </MapView>


                        <LocationTracker ref={locationTrackerRef} 
                                        points={trail_points} 
                                        preferences={preferences} 
                                        points_history={points_history} 
                                        navigation={navigation}
                                        onTravelledDistanceUpdate={updateTravelledDistance}/>


                        <View style={styles.buttons_container}>
                            <TouchableOpacity 
                                disabled={startDisable}
                                style={[styles.button, {backgroundColor: startDisable ? COLORS.light_gray : COLORS.logo_yellow}]} 
                                onPress={handleStart}>
                                <Text style={styles.text_button}>START</Text>
                            </TouchableOpacity>

                            <TouchableOpacity 
                                disabled={stopDisable}
                                style={[styles.button, {backgroundColor: stopDisable ? COLORS.light_gray : COLORS.logo_yellow}]} 
                                onPress={handleStop}>
                                <Text style={styles.text_button}>STOP</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                )}

                
                {trail_points.map((point) => (
                    <PointsItem key={point.id} point={point}/>
                ))}


            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: COLORS.light_blue,
        padding: 20,
    },

    map_container: {
        height: 500,
        marginTop: 10,
    },

    trail_container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },

    title_container: {
        flex: 4,
    },

    text_container: {
        flex: 1,
        alignItems: 'flex-end',
    },

    trail_name: {
        fontSize: 50,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        textTransform: 'uppercase',
    },

    trail_details: {
        fontSize: 18,
        color: COLORS.white,
        marginBottom: 5,
        fontWeight: 'bold',
    },
    
    trail_image: {
        width: 150,
        height: 200,
        alignSelf: 'center',
        marginTop: 20,
    },

    title: {
        fontSize: 36,
        fontWeight: 'bold',
        color: COLORS.white,
        marginTop: 20,
    },

    text: {
        fontSize: 20,
        color: COLORS.white,
        marginTop: 10,
    },

    buttons_container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginTop: 20,
        marginBottom: 10,
    },

    button: {
        backgroundColor: COLORS.logo_yellow,
        padding: 7,
        borderRadius: 10,
        alignItems: 'center',
        width:"45%",
    },

    text_button: {
        fontSize: 24,
        color: COLORS.white,
        fontWeight: 'bold',
    },
});

export default TrailPage;