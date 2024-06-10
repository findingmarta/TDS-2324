import React from 'react';
import {StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useSelector} from 'react-redux';

import { COLORS } from '../style/colors';

function HistoryPointItem ({history_point}) {
    const navigation = useNavigation();

    const points = useSelector((state) => state.trails.points)
    const point = points.find(point => point.id === history_point.id);

    const handlePress = (point) => {
        navigation.navigate('PointPage', point, navigation);
    }

    const truncatedDesc = history_point.desc.length > 250 ? history_point.desc.substring(0, 250) + '...' : history_point.desc;

    return (
        <TouchableHighlight 
            underlayColor="#00FFF"
            onPress={() => handlePress({point: point})}>

            <View style={styles.historyPoint_container}>
                <Text style={styles.historyPoint_name}>{history_point.name}</Text>
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