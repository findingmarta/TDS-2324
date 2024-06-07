import React, { useEffect } from 'react';
import {Image, Linking, StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import { useDispatch, useSelector } from "react-redux"
import { useNavigation } from '@react-navigation/native';

import { fetchUserData } from '../features/userSlice';
import { ScrollView } from 'react-native-gesture-handler';

import { COLORS } from '../style/colors';

function Profile () {
    // const dispatch = useDispatch();
    // const user = useSelector((state) => state.user.user);
    const navigation = useNavigation();

    // useEffect(() => {
    //     if (user.username === '')
    //         dispatch(fetchUserData());
    // }, []);


    function handleHistoryPress() {
        navigation.navigate('HistoryTrail');    
    }

    function handleSettingsPress() {
        navigation.navigate('Settings');    
    }

    return (
        <View>
            
            <Image source={require('../images/profile_logo_circle.png')} style={styles.logo} />
            <Text>
                {/* {user.username} */}
                username
            </Text>
            <Text>
                {/* {user.firstname} */}
                firestname lastname
            </Text>        
            <TouchableOpacity style={styles.button} onPress={handleHistoryPress}>
                    <Text style={styles.textButton}> HISTORY </Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.button} onPress={handleSettingsPress}>
                    <Text style={styles.textButton}> SETTINGS </Text>
            </TouchableOpacity>
        </View>
    );

}
const styles = StyleSheet.create({
    
    logo: {
        width: 200,
        height: 200,
        alignSelf: 'center',
        marginTop: 150,
        marginBottom: 50,
    },
    button: {
        backgroundColor: COLORS.logo_blue,
        padding: 10,
        margin: 10,
        borderRadius: 5,
        width: "50%",
        alignSelf: 'center',
    },
    textButton: {
        color: COLORS.white,
        textAlign: 'center',
        fontWeight: 'bold',
        fontSize: 16
    },
});

export default Profile;