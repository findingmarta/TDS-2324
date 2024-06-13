import React, { useEffect, useState } from 'react';
import { Image, StyleSheet, Text, View, TouchableOpacity, Linking } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { useNavigation } from '@react-navigation/native';
import { useDispatch, useSelector } from "react-redux"

import { addPoint } from '../features/historySlice';

import { COLORS } from '../style/colors';


function PointPage ({route}) {
    const point = route.params.point;

    const [mediaUrl, setMediaUrl] = useState('');

    useEffect(() => {
        let url = '';
        for (let i = 0; i < point.media.length; i++) {
            if (point.media[i].media_type === 'I') {
                url = point.media[i].media_file;
                break;  // Assuming you want the first image only
            }
        }
        setMediaUrl(url);
    }, [point.media]);

    const navigation = useNavigation();
    const dispatch = useDispatch();

    // Get points history
    const points_history = useSelector((state) => state.history.points);
    const isPremium = useSelector((state) => state.user.isPremium);

    // Check if point was visited
    const visited = points_history.some(p => p.id === point.id);

    function handleLocationPress(point){
        const { pin_lat, pin_lng, pin_name } = point;
        const link = `https://www.google.com/maps/search/?api=1&query=${pin_lat},${pin_lng}(${encodeURIComponent(pin_name)})`;
        Linking.openURL(link);
    }

    function handleMediaPress(){ 
        navigation.navigate('MediaPage',point)
    }

    function markVisited(point){
        dispatch(addPoint(point));
    }

    return (
        <View style={styles.container}>
            <ScrollView showsVerticalScrollIndicator={false}>

                <View style={styles.point_container}>
                    <View style={styles.title_container}>
                        <Text style={styles.pin_name}>{point.pin_name}</Text>
                    </View>
                </View>        


                {mediaUrl !== '' && (
                    <Image style={styles.point_image} source={{ uri: mediaUrl }} />
                )}
                
                <View style={styles.buttons_container}>
                    <TouchableOpacity style={[styles.button,{backgroundColor: COLORS.logo_yellow}]} onPress={() => handleLocationPress(point)}>
                        <Text style={styles.text_button}>LOCATION</Text>
                    </TouchableOpacity>    
                </View>  

                <View style={styles.container_desc}>
                    <Text style={styles.title}>DESCRIPTION</Text>
                    
                    {isPremium && (
                        <TouchableOpacity style={[styles.button_media, { opacity: point.media.length===0 ? 0.5 : 1 }]} onPress={handleMediaPress} disabled={point.media.length===0}>
                            <Image source={require('../images/media_logo.png')} style={styles.image_media}/>
                        </TouchableOpacity>
                    )}

                </View>
                <Text style={styles.text}>{point.pin_desc}</Text>
                <Text style={styles.title}>PROPERTIES</Text>

                {point.rel_pin.map((real) => (
                    <Text key={real.id} style={styles.text}>{"- " + real.attrib + ": " + real.value}</Text>
                ))}
                
                
                <View style={styles.buttons_container}>
                    {isPremium && (
                        <TouchableOpacity 
                            disabled={visited} 
                            style={[styles.button, {backgroundColor: visited ? COLORS.light_gray : COLORS.logo_yellow}]}
                            onPress={() => markVisited(point)}>
                            <Text style={styles.text_button}>MARK AS VISITED</Text>
                        </TouchableOpacity>
                    )}
                </View>

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

    point_image:{
        width: 240,
        height:300,
        alignSelf: 'center'
    },
    
    point_container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },

    title_container: {
        flex: 4,
    },

    pin_name: {
        fontSize: 50,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        textTransform: 'uppercase',
    },
    
   

    title: {
        fontSize: 36,
        fontWeight: 'bold',
        color: COLORS.white,
        marginTop: 15,
    },

    text: {
        fontSize: 20,
        color: COLORS.white,
        marginTop: 10,
    },

    
    button: {
        padding: 7,
        borderRadius: 10,
        alignItems: 'center',
        alignSelf: 'center',
        width:"95%",
        marginTop: 15,
    },

    text_button: {
        fontSize: 18,
        color: COLORS.white,
        fontWeight: 'bold',
    },

    container_desc: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },

    button_media: {
        marginRight: 40,
        marginTop: 20,
        width: 20,
        height:20,
    },

    image_media: {
        height: 50,
        width: 50,
    },
});

export default PointPage;