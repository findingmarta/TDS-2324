import React from 'react';
import {StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useSelector} from 'react-redux';

import { COLORS } from '../style/colors';

function HistoryTrailItem ({history_trail}) {
    const navigation = useNavigation();

    const trails = useSelector((state) => state.trails.trails)
    const trail = trails.find(trail => trail.id === history_trail.id);

    const handlePress = (trail) => {
        navigation.navigate('TrailPage', trail, navigation);
    }

    return (
        <TouchableHighlight 
            underlayColor="#00FFF"
            onPress={() => handlePress({trail: trail})}>

            <View style={styles.historyTrail_container}>
                <Text style={styles.historyTrail_name}>{history_trail.name}</Text>
                <Text style={styles.historyTrail_details}>Duration: {history_trail.duration} minutes</Text>
                <Text style={styles.historyTrail_details}>Difficulty: {history_trail.difficulty}</Text>
                <Text style={styles.historyTrail_details}>Date: {history_trail.date}</Text>
                <Text style={styles.historyTrail_details}>Travelled Distance: {history_trail.travelled_distance} meter(s)</Text>
                <Text style={styles.historyTrail_details}>Travelled Time: {history_trail.travelled_time} minutes</Text>
            </View>
        </TouchableHighlight>
    )

}

const styles = StyleSheet.create({
    historyTrail_container: {
        flexDirection: 'column',
        margin: 7,
        padding: 20,
        backgroundColor: COLORS.lighter_blue,

        // Android shadow property
        elevation: 10,
    },

    history_container: {
        flexDirection: 'row',
        marginBottom: 10,
    },

    historyTrail_name: {
        fontSize: 24,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        textTransform: 'uppercase',
    },

    historyTrail_details: {
        fontSize: 18,
        color: COLORS.white,
        marginBottom: 5,
    },
});

export default HistoryTrailItem;