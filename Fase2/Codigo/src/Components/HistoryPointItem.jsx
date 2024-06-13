import React from 'react';
import {StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { useNavigation } from '@react-navigation/native';

import { COLORS } from '../style/colors';

function HistoryPointItem ({history_point}) {
    const navigation = useNavigation();

    const handlePress = (point) => {
        navigation.navigate('PointPage', point, navigation);
    }

    const truncatedDesc = history_point.pin_desc.length > 250 ? history_point.pin_desc.substring(0, 250) + '...' : history_point.pin_desc;

    return (
        <TouchableHighlight 
            underlayColor="#00FFF"
            onPress={() => handlePress({point: history_point})}>

            <View style={styles.historyPoint_container}>
                <Text style={styles.historyPoint_name}>{history_point.pin_name}</Text>
                <Text style={styles.historyPoint_details}>{truncatedDesc}</Text>
            </View>
        </TouchableHighlight>
    )

}

const styles = StyleSheet.create({
    historyPoint_container: {
        flexDirection: 'column',
        margin: 7,
        padding: 20,
        backgroundColor: COLORS.lighter_blue,
        elevation: 10,
    },

    history_container: {
        flexDirection: 'row',
        marginBottom: 10,
    },

    historyPoint_name: {
        fontSize: 24,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        textTransform: 'uppercase',
    },

    historyPoint_details: {
        fontSize: 18,
        color: COLORS.white,
        marginBottom: 5,
    },
});

export default HistoryPointItem;