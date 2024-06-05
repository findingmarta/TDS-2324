import React, { useEffect, useState } from 'react';
import {Image, StyleSheet, Text, View, Button, TouchableOpacity } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import MapView, {Marker, Polyline} from 'react-native-maps';

import PointsItem from '../Components/PointsItem';
import { COLORS } from '../style/colors';

function getPoints (edges) {
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

function TrailPage ({route}) {
    const trail = route.params.trail;

    const points = getPoints(trail.edges);

    const initialRegion = {
        latitude: points[0].pin_lat,
        longitude: points[0].pin_lng,
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
    };

    const polyCoords = points.map((point) => {
        return {
            latitude: point.pin_lat,
            longitude: point.pin_lng,
        }
    });

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

                
                <MapView style={styles.map_container} initialRegion={initialRegion}>
                    <Polyline coordinates={polyCoords} strokeColor={COLORS.logo_blue} strokeWidth={5} />
                    {points.map((marker) => (
                        <Marker
                            key={marker.id}
                            coordinate={{latitude: marker.pin_lat, longitude: marker.pin_lng}}
                            title={marker.pin_name}
                        />
                    ))}
                </MapView>

                <View style={styles.buttons_container}>
                    <TouchableOpacity style={styles.button} onPress={() => {}}>
                        <Text style={styles.text_button}>START</Text>
                    </TouchableOpacity>

                    <TouchableOpacity style={styles.button} onPress={() => {}}>
                        <Text style={styles.text_button}>STOP</Text>
                    </TouchableOpacity>
                </View>

                {points.map((point) => (
                    <PointsItem key={point.id} point={point}/>
                ))}

            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
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