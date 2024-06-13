import React, { useEffect } from 'react';
import {View} from 'react-native';
import { useDispatch, useSelector } from "react-redux"
import { ScrollView } from 'react-native-gesture-handler';

import { fetchAppData } from '../features/appSlice';
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