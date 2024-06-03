import React, { useEffect } from 'react';
import {Image, Linking, StyleSheet, Text, View} from 'react-native';
import { Divider, FAB } from '@rneui/themed';
import { useDispatch, useSelector } from "react-redux"

import { fetchAppData } from '../features/appSlice';
import { ScrollView } from 'react-native-gesture-handler';

import { COLORS } from '../style/colors';

function Profile () {
    return (
        <View>
            <Text>
                Profile
            </Text>
        </View>
    );

}

export default Profile;