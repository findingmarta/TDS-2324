import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import Profile from '../Screens/Profile';
import HistoryTrail from '../Screens/HistoryTrail';

const profileStack = createStackNavigator()

export default function TrailsStack() {

    return (
        <profileStack.Navigator>
            <profileStack.Screen name="Profile" component={Profile} options={{ headerShown: false }} />
            <profileStack.Screen name="HistoryTrail" component={HistoryTrail} options={{ headerShown: false }} />
            {/*<profileStack.Screen name="Settings" component={Settings} options={{ headerShown: false }} />*/}
        </profileStack.Navigator>
    );
}