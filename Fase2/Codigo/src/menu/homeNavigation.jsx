import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import TrailPage from '../Screens/TrailPage';
import Trails from '../Screens/Trails';
import PointPage from '../Screens/PointPage';
import MediaPage from '../Screens/MediaPage';

const trailStack = createStackNavigator()

export default function TrailsStack() {    

    return (
        <trailStack.Navigator>
            <trailStack.Screen name="Trails" component={Trails} options={{ headerShown: false }} />
            <trailStack.Screen name="TrailPage" component={TrailPage} options={{ headerShown: false }} />        
            <trailStack.Screen name="PointPage" component={PointPage} options={{ headerShown: false }} />
            <trailStack.Screen name="MediaPage" component={MediaPage} options={{ headerShown: false }} />
        </trailStack.Navigator>
    );
}