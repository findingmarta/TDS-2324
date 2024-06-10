import React from 'react';
import {Image, StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { useNavigation } from '@react-navigation/native';

import { COLORS } from '../style/colors';

function TrailsItem ({trail}) {
    const navigation = useNavigation();

    const handlePress = (trail) => {
        navigation.navigate('TrailPage', trail, navigation);
    }

    return (
        <TouchableHighlight key={trail.id} 
                            underlayColor="#00FFF"
                            onPress={() => handlePress({trail: trail})}>
            
            <View style={styles.trail_container}>
                <View style={styles.text_container}>
                    <Text style={styles.trail_name}>{trail.trail_name}</Text>
                    <Text style={styles.trail_details}>Duration: {trail.trail_duration}</Text>
                    <Text style={styles.trail_details}>Difficulty: {trail.trail_difficulty}</Text>
                </View>
                <Image style={styles.trail_image} source={{uri: trail.trail_img}}/>
            </View>
        </TouchableHighlight>
    );
}

const styles = StyleSheet.create({
    trail_container: {
        flexDirection: 'row',
        margin: 7,
        padding: 20,
        backgroundColor: COLORS.light_blue,

        // Android shadow property
        elevation: 10,
    },

    text_container: {
        flex: 1,
    },

    trail_name: {
        fontSize: 24,
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
        width: 100,
        height: 100,
    },
});

export default TrailsItem;