import React, { useEffect } from 'react';
import {Image, Linking, StyleSheet, Text, View} from 'react-native';
import { Divider, FAB } from '@rneui/themed';
import { useDispatch, useSelector } from "react-redux"
import { ScrollView } from 'react-native-gesture-handler';

import { fetchAppData } from '../features/appSlice';
import { fetchTrailsData } from '../features/trailsSlice';
import { putUser } from '../features/userSlice';

import { COLORS } from '../style/colors';

function handleLinkPress(url) {
    if (url)
        Linking.openURL(url);
}

function handlePhonePress(phone) {
    if (phone)
        Linking.openURL(`tel:${phone}`);
}

function handleMailPress(mail) {
    if (mail)
        Linking.openURL(`mailto:${mail}`);
}

function getUser (session, token, dispatch) {
    console.log("Fetching User's data...")
    fetch('https://39b6-193-137-92-72.ngrok-free.app/user', {
        method: 'GET',
        headers : {
            'Content-Type': 'application/json',
            'Cookie' : `csrftoken=${  token  };sessionid=${  session}`,
        }
    })
    .then(response => {
        if (!response.ok) {
            console.log("Failed to fetch User's data")
            throw new Error('Failed to fetch User\'s data');
        }
        return response.json();
    })
    .then(user => {  
        dispatch(putUser(user));
    })
    .catch(error => {
        console.error('Error fetching User\'s data: ', error);
        throw new Error('Error fetching User\'s data: ', error);
    });
}

function Home() {
    const dispatch = useDispatch();

    // GET USER DATA
    const cookies = useSelector((state) => state.user.cookies);
    useEffect(() => {
        if (cookies.sessionid !== '' && cookies.csrftoken !== '')
            getUser(cookies.sessionid, cookies.csrftoken, dispatch);
    }, []);

    // GET APP DATA
    const app = useSelector((state) => state.app.app);
    useEffect(() => {
        if (app.appName === '')
            dispatch(fetchAppData());
    }, []);

    // GET TRAILS DATA
    const trails = useSelector((state) => state.trails.trails);
    useEffect(() => {
        if (trails.length == 0)
            dispatch(fetchTrailsData());
    }, []);

    return (
        <View style={styles.wrapper}>
            <ScrollView showsVerticalScrollIndicator={false}>

                <View style={styles.container_relative}>  
                    <Image source={require('../images/maps_logo.png')} style={styles.mapslogo} />
                    
                    <View style={styles.container_text}>
                        <Text style={styles.warning}>WARNING</Text>
                            <Text style={styles.warning_text}>In order to use this application you need to have{' '}
                            <Text style={styles.linkText} onPress={() => handleLinkPress('https://play.google.com/store/apps/details?id=com.google.android.apps.maps')}>Google Maps </Text>
                            {' '}installed.</Text>
                    </View>
                </View>
                
                <Divider margin={20} width={1} color={COLORS.light_gray} />

                <View style={styles.container}>
                    <Image source={require('../images/logo.png')} style={styles.logo} />        
                </View>

                <View style={styles.container}>
                    <Text style={styles.appDescription}>{app.appDescription}</Text>                
                    <Text style={styles.text}>{app.appLandingPage}</Text>
                </View>

                <View>
                    <Text style={styles.title}>PARTNERS</Text>

                    {app.partners.map((partner) => (
                        <View key={partner.partner_name} style={styles.partner_container}>
                            <Text style={styles.partner_name}>{partner.partner_name}</Text>
                            <Text style={styles.partner_details} onPress={() => handleMailPress(partner.partner_mail)}>{partner.partner_mail}</Text>
                            <Text style={styles.partner_details} onPress={() => handlePhonePress(partner.partner_phone)}>{partner.partner_phone}</Text>
                            <Text style={styles.partner_details} onPress={() => handleLinkPress(partner.partner_url)}>{partner.partner_url}</Text>
                        </View>
                    ))}
                </View>

            </ScrollView>

            <FAB size='large'
                color={COLORS.logo_yellow}
                icon={
                    <Image source={require('../images/facebook_logo.png')} style={{ width: 25, height: 25 }} />
                }
                placement='right'
                style={styles.fab}
                onPress={() => handleLinkPress(app.socials[0].social_url)}
            />

        </View>
    )
}

const styles = StyleSheet.create({
    wrapper: {
        flex: 1,
      },

    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        margin: 20,
    },

    container_relative: {
        flexDirection: 'row',
        alignItems: 'center',
        padding: 20,
        justifyContent: 'center',
    },

    title: {
        fontSize: 32,
        margin: 20,
        color: COLORS.dark_gray,
        fontWeight: 'bold',
    },

    text: {
        fontSize: 20,
        marginTop: 20,
    },

    logo: {
        width: 250,
        height: 200,
        margin: 20,
        marginBottom: 1,
    },

    mapslogo: {
        width: 70,
        height: 100,
        marginRight: 20,
    },

    container_text: {
        flex: 1,
    },

    warning: {
        fontSize: 32,
        color: COLORS.dark_gray,
    },

    warning_text: {
        fontSize: 20,
        color: COLORS.dark_gray,
    },

    linkText: {
        fontSize: 20,
        color: COLORS.logo_yellow,
        textDecorationLine: 'underline',
      },

    appDescription: {
        fontSize: 28,
        color: COLORS.logo_blue,
        textAlign: 'center',
    },

    partner_container: {
        margin: 20,
        marginTop: 1,
        padding: 20,
        backgroundColor: COLORS.light_blue,

        // Android shadow property
        elevation: 10,
    },

    partner_name: {
        fontSize: 24,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
    },

    partner_details: {
        fontSize: 18,
        color: COLORS.white,
        marginBottom: 5,
    }, 

    fab: {
        position: 'absolute',
        bottom: 16,
        right: 16,
      },

});

export default Home;