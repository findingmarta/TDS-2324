import React, { useEffect } from 'react';
import {View} from 'react-native';
import { useDispatch, useSelector } from "react-redux"

import { fetchTrailsData } from '../features/trailsSlice';
import { ScrollView } from 'react-native-gesture-handler';

import TrailsItem from '../Components/TrailsItem';

function Trails () {
    const dispatch = useDispatch();
    const trails = useSelector((state) => state.trails.trails);

    useEffect(() => {
        if (trails.length == 0)
            dispatch(fetchTrailsData());
    }, []);
    

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