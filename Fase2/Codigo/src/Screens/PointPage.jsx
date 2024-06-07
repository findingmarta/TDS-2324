import React, { useEffect, useState } from 'react';
import {Image, StyleSheet, Text, View, Button, TouchableOpacity } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { useNavigation } from '@react-navigation/native';
import { COLORS } from '../style/colors';



function PointPage ({route}) {
    // const point = route.params.point;
    
    // function media(){
    //     const url = ''
    //     for(let i = 0; i < point.media.length;i++){
    //         if(point.media[i].media_type == 'I'){
    //             url = point.media[i].media_file
    //         }
    //     }
    //     return url;
    // }

    const navigation = useNavigation();
    function handleMediaPress(){ navigation.navigate('MediaPage')}

    return (
        <View style={styles.container}>
            <ScrollView showsVerticalScrollIndicator={false}>

                <View style={styles.point_container}>
                    <View style={styles.title_container}>
                        <Text style={styles.pin_name}>Titulo</Text>
                    </View>
                </View>
                <Image style={styles.point_image} source={require('../images/Sé-de-Braga.jpg')} />
                
                {/* {media() !== '' && (
                    <Image style={styles.point_image} source={{ uri: media() }} />
                )} */}
                <View style={styles.container_desc}>
                    <Text style={styles.title}>DESCRIPTION</Text>
                    <TouchableOpacity style={styles.button_media} onPress={handleMediaPress}>
                        <Image source={require('../images/media_logo.png')} style={styles.image_media}/>
                    </TouchableOpacity>
                </View>
                <Text style={styles.text}>wefjlrfwrglgçgnlgjnlnrlnwrlnjwenglkejnlgnwrlblwnwekgjwlgbçgbwleoihwçgwknmehjnnthjoçijhçoestmjeoihjçsknhwçohnçsoignhwponskoighwçnbsçrouhwpçgnbeçsgiçbeçhbuprhbnºgbjeçyj4çohjnb ohjpw460yujpw4jywç</Text>
                <Text style={styles.title}>Propreties</Text>
                <Text style={styles.text}>wefjlrfwrglgçgnlgjnlnrlnwrlnjwenglkejnlgnwrlblwnwekgjwlgbçgbwleoihwçgwknmehjnnthjoçijhçoestmjeoihjçsknhwçohnçsoignhwponskoighwçnbsçrouhwpçgnbeçsgiçbeçhbuprhbnºgbjeçyj4çohjnb ohjpw460yujpw4jywç</Text>
                
                <View style={styles.buttons_container}>
                    <TouchableOpacity style={styles.button} onPress={() => {}}>
                        <Text style={styles.text_button}>MARK AS VISITED</Text>
                    </TouchableOpacity>
                </View>

            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: COLORS.light_blue,
        padding: 20,
    },

    
    point_container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },

    title_container: {
        flex: 4,
    },

    pin_name: {
        fontSize: 50,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        textTransform: 'uppercase',
    },
    
   

    title: {
        fontSize: 36,
        fontWeight: 'bold',
        color: COLORS.white,
        marginTop: 15,
    },

    text: {
        fontSize: 20,
        color: COLORS.white,
        marginTop: 10,
        marginBottom: 15,
    },

    
    button: {
        backgroundColor: COLORS.logo_yellow,
        padding: 7,
        borderRadius: 10,
        alignItems: 'center',
        alignSelf: 'center',
        width:"95%",
        marginTop: 15,
    },

    text_button: {
        fontSize: 18,
        color: COLORS.white,
        fontWeight: 'bold',
    },

    container_desc: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },

    button_media: {
        marginRight: 40,
        marginTop: 20,
        width: 20,
        height:20,
    },

    image_media: {
        height: 50,
        width: 50,
    },
});

export default PointPage;