import React from 'react';
import {View} from 'react-native';
import { useSelector } from "react-redux"

import { ScrollView } from 'react-native-gesture-handler';

import TrailsItem from '../Components/TrailsItem';

function Trails () {
    const trails = useSelector((state) => state.trails.trails);   

    return (
        <ScrollView showsVerticalScrollIndicator={false}>
            <View>
                {trails.map((trail) => (
                    <TrailsItem key={trail.id} trail={trail}/>
                ))}
            </View>
        </ScrollView>
    );
}



export default Trails;