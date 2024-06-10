import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import Profile from '../Screens/Profile';
import HistoryTrail from '../Screens/HistoryTrail';
import HistoryPoint from '../Screens/HistoryPoint';
import TrailPage from '../Screens/TrailPage';
import PointPage from '../Screens/PointPage';
import Settings from '../Screens/Settings';

const profileStack = createStackNavigator()

export default function TrailsStack() {

    return (
        <profileStack.Navigator>
            <profileStack.Screen name="Profile" component={Profile} options={{ headerShown: false }} />
            <profileStack.Screen name="HistoryTrail" component={HistoryTrail} options={{ headerShown: false }} />
            <profileStack.Screen name="HistoryPoint" component={HistoryPoint} options={{ headerShown: false }} />
            <profileStack.Screen name="TrailPage" component={TrailPage} options={{ headerShown: false }} />
            <profileStack.Screen name="PointPage" component={PointPage} options={{ headerShown: false }} />
            <profileStack.Screen name="Settings" component={Settings} options={{ headerShown: false }} />
        </profileStack.Navigator>
    );
}