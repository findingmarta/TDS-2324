import React from 'react';
import {StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { useNavigation } from '@react-navigation/native';

import { COLORS } from '../style/colors';

function PointsItem ({point}) {
    const navigation = useNavigation();

    const handlePress = (point) => {
        //navigation.navigate('TrailPage', trail, navigation);
        navigation.navigate('Home');
    }

    const truncatedDesc = point.pin_desc.length > 250 ? point.pin_desc.substring(0, 250) + '...' : point.pin_desc;

    return (
        <TouchableHighlight key={point.id} 
                            underlayColor="#00FFF"
                            onPress={() => handlePress({point: point})}>
            
            <View style={styles.point_container}>
                <Text style={styles.point_name}>{point.pin_name}</Text>
                <Text style={styles.point_desc}>{truncatedDesc}</Text>                    
            </View>
        </TouchableHighlight>
    );
}

const styles = StyleSheet.create({
    point_container: {
        flexDirection: 'column',
        margin: 7,
        padding: 20,
        backgroundColor: COLORS.lighter_blue,

        // Android shadow property
        elevation: 10,
    },

    point_name: {
        fontSize: 24,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        textTransform: 'uppercase',
    },

    point_desc: {
        fontSize: 18,
        color: COLORS.white,
        marginBottom: 5,
    },
});

export default PointsItem;