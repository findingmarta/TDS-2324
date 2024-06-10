import {Image, StyleSheet, View,TouchableOpacity} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import React, { useState } from 'react';
import { useNavigation } from '@react-navigation/native';
import { useSelector } from "react-redux"

import HistoryTrailItem from '../Components/HistoryTrailItem';

import { COLORS } from '../style/colors';

function HistoryTrail() {
    const navigation = useNavigation();

    // GET HISTORY TRAIL DATA
    const history_trails = useSelector((state) => state.history.trails);  

    // DEFINE BUTTON'S STATE
    const [buttonTrail, setButtonTrail] = useState(true);
    const [buttonPoint, setButtonPoint] = useState(false);

    // HANDLE BUTTON PRESS
    const handleTrailPress = () => {
        setButtonTrail(true);
        setButtonPoint(false);
        navigation.navigate('HistoryTrail');
    }

    const handlePointPress = () => {
        setButtonTrail(false);
        setButtonPoint(true);
        navigation.navigate('HistoryPoint');
    }

    return (
        <View style={styles.container}>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.history_container}>
                    <TouchableOpacity style={[styles.historyButton, buttonTrail ? styles.activeButton : styles.inactiveButton]} onPress={handleTrailPress}>
                        <Image source={require('../images/trail_logo.png')} style={styles.historyImage}/>
                    </TouchableOpacity>

                    <TouchableOpacity style={[styles.historyButton, buttonPoint ? styles.activeButton : styles.inactiveButton]} onPress={handlePointPress}>
                        <Image source={require('../images/point_logo.png')} style={styles.historyImage}/>
                    </TouchableOpacity>
                </View>

                <View>
                    {history_trails.map((trail) => (
                        <HistoryTrailItem key={trail.id} history_trail={trail}/>
                    ))}
                </View>
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'column',
        padding: 10,
    },

    history_container: {
        flexDirection: 'row',
        alignSelf:'center'
    },

    historyButton:{
        backgroundColor: COLORS.logo_yellow,
        margin:1,
        marginTop:10,
        borderRadius: 10,
        paddingVertical:10,
        paddingHorizontal:15, 
    },

    historyImage:{
        height: 30,
        width: 25,
    },

    activeButton:{
        backgroundColor: COLORS.logo_yellow,
    },

    inactiveButton:{
        backgroundColor: COLORS.light_yellow,
    }
});

export default HistoryTrail;