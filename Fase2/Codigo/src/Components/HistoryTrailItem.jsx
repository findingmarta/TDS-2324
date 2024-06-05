import React from 'react';
import {StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { useNavigation } from '@react-navigation/native';

import { COLORS } from '../style/colors';

function HistoryTrailItem () {
    const navigation = useNavigation();

    const handlePress = () => {
        //navigation.navigate('TrailPage', trail, navigation);
        navigation.navigate('Home');
    }

    return (
        //<TouchableHighlight key={history.id} 
        <TouchableHighlight 
            underlayColor="#00FFF"
        //    onPress={() => handlePress({history: history})}>
            onPress={()=>handlePress}>

            <View style={styles.historyTrail_container}>
                <Text style={styles.historyTrail_name}>AAAAAAAAAAAAAAAAAAAAA</Text>
                <Text style={styles.historyTrail_details}>Duration: </Text>
                <Text style={styles.historyTrail_details}>Difficulty: </Text>
                <Text style={styles.historyTrail_details}>Date: </Text>
                <Text style={styles.historyTrail_details}>Travelled Distance: </Text>
                <Text style={styles.historyTrail_details}>Travelled Time: </Text>
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