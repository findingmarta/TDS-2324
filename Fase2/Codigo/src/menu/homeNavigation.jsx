import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import TrailPage from '../Screens/TrailPage';
import Trails from '../Screens/Trails';
import PointPage from '../Screens/PointPage';


const trailStack = createStackNavigator()

export default function TrailsStack() {    

    return (
        <trailStack.Navigator>
            <trailStack.Screen name="Trails" component={Trails} options={{ headerShown: false }} />
            <trailStack.Screen name="TrailPage" component={TrailPage} options={{ headerShown: false }} />        
            <trailStack.Screen name="PointPage" component={PointPage} options={{ headerShown: false }} />
            {/* <trailStack.Screen name="Points" component={PinListSection} options={{ headerShown: false }} />
            <trailStack.Screen name="Media" component={PinMedia} options={{ headerShown: false }} /> */}
        </trailStack.Navigator>
    );
}