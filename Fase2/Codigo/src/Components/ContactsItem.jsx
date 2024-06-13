import React from 'react';
import {StyleSheet, Text, View,TouchableOpacity, Linking} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { COLORS } from '../style/colors';

function handleLinkPress(url) {
    if (url)
        Linking.openURL(url);
}

function handlePhonePress(phone) {
    if (phone)
        Linking.openURL(`tel:${phone}`);
}

function handleMailPress(mail) {
    if (mail)
        Linking.openURL(`mailto:${mail}`);
}

function ContactsItem ({Contact}) {

    return (
        <View>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.container}>
                        <Text style={styles.title}>
                            {Contact.contact_name}
                        </Text>
                        <Text style={styles.details} onPress={()=>handleMailPress(Contact.contact_mail)}>
                            Email: {Contact.contact_mail}
                        </Text>
                        <Text style={styles.details}>
                            Phone: {Contact.contact_phone}
                        </Text>
                        <Text style={styles.details} onPress={()=>handleLinkPress(Contact.contact_url)}>
                            Website: {Contact.contact_url}
                        </Text>
                        
                        <TouchableOpacity style={styles.button} onPress={()=>handlePhonePress(Contact.contact_phone)}>
                             <Text style={styles.textButton}> CALL </Text>
                        </TouchableOpacity>


                </View> 
            </ScrollView>
        </View>
    );
}
const styles = StyleSheet.create({

container: {
    backgroundColor: COLORS.lighter_blue,
    margin: 7,
    padding: 20,
},

title: {
    fontSize: 24,
    color: COLORS.logo_yellow,
    fontWeight: 'bold',
    marginBottom: 15,
    textTransform: 'uppercase',
    },

details: {
    fontSize: 17,
    color: COLORS.white,
    marginBottom: 5,
    },
button: {
    backgroundColor: COLORS.logo_yellow,
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
    marginTop: 10,
    },
textButton: {
    color: COLORS.white,
    fontSize: 20,
    fontWeight: 'bold',
    },


});

export default ContactsItem;