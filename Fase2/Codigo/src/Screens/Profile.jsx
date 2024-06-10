import React, { useEffect } from 'react';
import {Image, StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import { useDispatch, useSelector } from "react-redux"
import { useNavigation } from '@react-navigation/native';
import { ScrollView } from 'react-native-gesture-handler';

import { COLORS } from '../style/colors';

function Profile () {
    const navigation = useNavigation();

    const user = useSelector((state) => state.user.user);
    const isPremium = useSelector((state) => state.user.isPremium);

    function handleHistoryPress() {
        navigation.navigate('HistoryTrail');    
    }

    function handleSettingsPress() {
        navigation.navigate('Settings');    
    }

    return (
        <View>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.container}>
                    <Image source={require('../images/profile_logo_circle.png')} style={styles.logo} />

                    {isPremium && (
                        <Image source={require('../images/verified_logo.png')} style={styles.verifiedlogo} />
                    )}
                </View>
                <Text style={styles.text1}>@{user.username}</Text>
                <Text style={styles.text2}> {user.first_name} {user.last_name}</Text>

                {isPremium && (
                    <View>
                        <TouchableOpacity style={styles.button} onPress={handleHistoryPress}>
                            <Text style={styles.textButton}> HISTORY </Text>
                        </TouchableOpacity>
                
                        <TouchableOpacity style={styles.button} onPress={handleSettingsPress}>
                            <Text style={styles.textButton}> SETTINGS </Text>
                        </TouchableOpacity>
                    </View>
                )}

            </ScrollView>
        </View>
    );

}
const styles = StyleSheet.create({
    
    container: {
        height: 370,
    },

    logo: {
        width: 200,
        height: 200,
        alignSelf: 'center',
        marginTop: 150,
        marginBottom: 20,
    },

    verifiedlogo: {
        width: 70,
        height: 70,
        alignSelf: 'center',
        marginTop: -220,
        marginLeft: 150,
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
    text1: {
        fontSize:22,
        alignSelf: 'center',
        marginBottom: 2,
    },

    text2: {
        fontSize:22,
        alignSelf: 'center',
        marginBottom: 30,
    },
});

export default Profile;