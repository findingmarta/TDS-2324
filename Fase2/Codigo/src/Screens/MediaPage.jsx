import React, { useEffect, useState } from 'react';
import {Image, StyleSheet, Text, View, Button, TouchableOpacity } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';

import { COLORS } from '../style/colors';



function MediaPage ({route}) {
    return (
        <View>
            <ScrollView showsVerticalScrollIndicator={false}>
                <Text>Media</Text>
            </ScrollView>
        </View>
    );
}


export default MediaPage;