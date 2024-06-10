import React from 'react';
import {  Text,  View, Image, StyleSheet, TouchableOpacity } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { useNavigation } from '@react-navigation/native';

import { COLORS } from '../style/colors';

import Home from '../Screens/Home';
import Contacts from '../Screens/Contacts'
import TrailsStack from './homeNavigation';
import ProfileStack from './profileNavigation';

import LogoutButton from '../Components/LogoutButton';

const Tab = createBottomTabNavigator();

const screenOptions = {
    tabBarShowLabel:false,
    headerShown:false,
    tabBarStyle:{
        bottom: 0,
        right: 0,
        left: 0,
        elevation: 0,
        height: 60,
        backgroundColor: COLORS.logo_blue,
    }
};

function Logout () {
    const navigation = useNavigation();
    navigation.navigate('Login');
}

function BottomNavigation() {
    
    return (
        <Tab.Navigator initialRouteName="Home" screenOptions={screenOptions} backBehavior='initialRoute'>

            <Tab.Screen name="ProfileStack" component={ProfileStack} 
                options={{
                    tabBarIcon: ({focused})=>{
                    return (
                        <View style={styles.container}> 
                            <Image style={{width: 30, height:30, tintColor: focused ? COLORS.white : COLORS.black}} source={require('../images/profile_logo.png')}/>
                            <Text style={{fontSize: 12, color: focused ? COLORS.white : COLORS.black}}>PROFILE</Text>
                        </View>
            )}}}/>

            <Tab.Screen name="TrailsStack" component={TrailsStack} 
                options={{
                    tabBarIcon: ({focused})=>{
                    return (
                        <View style={styles.container}> 
                            <Image style={{width: 30, height:30, tintColor: focused ? COLORS.white : COLORS.black}} source={require('../images/trail_logo.png')}/>
                            <Text style={{fontSize: 12, color: focused ? COLORS.white : COLORS.black}}>TRAILS</Text>
                        </View>
            )}}}/>

            <Tab.Screen name="Home" component={Home} 
                options={{
                    tabBarIcon: ({focused})=>{
                        return (
                            <View style={styles.container}> 
                                <Image style={{width: 30, height:30, tintColor: focused ? COLORS.white : COLORS.black}} source={require('../images/home_logo.png')}/>
                                <Text style={{fontSize: 12, color: focused ? COLORS.white : COLORS.black}}>HOME</Text>
                            </View>
            )}}}/>

            <Tab.Screen name="Contacts" component={Contacts} 
                options={{
                    tabBarIcon: ({focused})=>{
                    return (
                        <View style={styles.container}> 
                            <Image style={{width: 30, height:30, tintColor: focused ? COLORS.white : COLORS.black}} source={require('../images/sos_logo.png')}/>
                            <Text style={{fontSize: 12, color: focused ? COLORS.white : COLORS.black}}>SOS</Text>
                        </View>
            )}}}/>

            <Tab.Screen name="Logout"
                options={{
                    tabBarIcon: ({ focused }) => (
                        <View style={styles.container}>
                        <Image
                            style={{ width: 30, height: 30, tintColor: focused ? COLORS.white : COLORS.black }}
                            source={require('../images/logout_icon.png')}/>
                        <Text style={{ fontSize: 12, color: focused ? COLORS.white : COLORS.black }}>LOGOUT</Text>
                        </View>
                    ),
                }}
            >
                    {() => <LogoutButton />}
            </Tab.Screen>
        </Tab.Navigator>
    );
}

const styles = StyleSheet.create({
    container:{
        alignItems: 'center',
        justifyContent: 'center',
    },
});

export default BottomNavigation;