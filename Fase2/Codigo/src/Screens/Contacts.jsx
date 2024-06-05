import React, { useEffect } from 'react';
import {Image, Linking, StyleSheet, Text, View} from 'react-native';
import { Divider, FAB } from '@rneui/themed';
import { useDispatch, useSelector } from "react-redux"

import { fetchAppData } from '../features/appSlice';
import { ScrollView } from 'react-native-gesture-handler';

import { COLORS } from '../style/colors';

import ContactsItem from '../Components/ContactsItem';

function Contacts () {
    const dispatch = useDispatch();
    const app = useSelector((state) => state.app.app);

    useEffect(() => {
        if (app.appName === '')
            dispatch(fetchAppData());
    }, []);

    return (
        <ScrollView showsVerticalScrollIndicator={false}>
            <View>
                {app.contacts.map((contact) => (
                    <View key={contact.contact_name}>
                        <ContactsItem Contact={contact} />
                    </View>
                ))}
            </View>
        </ScrollView>
    );

}


export default Contacts;