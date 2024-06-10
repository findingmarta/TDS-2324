import React, { useEffect, useState } from 'react';
import {StyleSheet, Text, View} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { COLORS } from '../style/colors';
import Carousel from '../Components/Carousel';
import AudioPlayer from '../Components/AudioPlayer';
import Download from '../Components/Download';
import RNFS from 'react-native-fs';

async function getImages(media) {
    const images = [];
    for (let i = 0; i < media.length; i++) {
        if (media[i].media_type === 'I' || media[i].media_type === 'V') {
            const split = media[i].media_file.split("/")
            const len = split.length
            const fileName = split[len-1]
            const localPath = `${RNFS.ExternalDirectoryPath}/${fileName}`;
            const exists = await RNFS.exists(localPath);
            const item = {
                id: media[i].id,
                type: media[i].media_type,
                uri: exists ? `file://${localPath}` : media[i].media_file
            };
            images.push(item);
        }
    }
    return images;
}

async function getAudio(media) {
    const audios = [];
    for (let i = 0; i < media.length; i++) {
        if (media[i].media_type === 'R') {
            const split = media[i].media_file.split("/")
            const len = split.length
            const fileName = split[len-1]
            const localPath = `${RNFS.ExternalDirectoryPath}/${fileName}`;
            const exists = await RNFS.exists(localPath);
            const item = {
                id: media[i].id,
                type: media[i].media_type,
                uri: exists ? `file://${localPath}` : media[i].media_file
            };
            audios.push(item);
        }
    }
    return audios;
}

function getUri(media){
    m = []
    for (let i = 0; i < media.length; i++) {
        m.push(media[i].media_file);
    }
    return m;
}

function MediaPage({ route }) {
    const media = route.params.media;
    const point_name = route.params.pin_name;
    const [data, setData] = useState([]);
    const [audios, setAudios] = useState([]);
    const [uris, setUris] = useState([]);

    useEffect(() => {
        async function fetchData() {
            // const new_item = {
            //     id: 15,
            //     media_file: "https://39b6-193-137-92-72.ngrok-free.app/media/guimaraes-castelo.jpg",
            //     media_pin: 2,
            //     media_type: "I"
            // };

            // const new_item2 = {
            //     id: 16,
            //     media_file: "https://39b6-193-137-92-72.ngrok-free.app/media/guimaraes-castelo.jpg",
            //     media_pin: 2,
            //     media_type: "I"
            // };

            // const new_item3 = {
            //     id: 17,
            //     media_file: "http://39b6-193-137-92-72.ngrok-free.app/media/A_Alma_Gente_Se_de_Braga_short.mp3",
            //     media_pin: 2,
            //     media_type: "R"
            // };

            // const updatedMedia = [...media, new_item, new_item2, new_item3];
            const updatedMedia = [...media];
            const images = await getImages(updatedMedia);
            const audios = await getAudio(updatedMedia);
            const uris = getUri(updatedMedia);

            setData(images);
            setAudios(audios);
            setUris(uris);
        }

        fetchData();
    }, [media]);

    return (
        <View style={styles.container}>
            <ScrollView>
                <Text style={styles.title}> {point_name} </Text>
                <Carousel data={data}/>
                {audios.length>0 && audios.map((audio) => (
                    <AudioPlayer key={audio.id} audioUrl={audio.uri} />
                ))}
                <Download fileUrls={uris}></Download> 
            </ScrollView>    
        </View>
    );
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        backgroundColor: COLORS.light_blue,
    },

    title:{
        fontSize: 50,
        color: COLORS.logo_yellow,
        fontWeight: 'bold',
        marginBottom: 15,
        marginTop: 15,
        textTransform: 'uppercase',
    },
})

export default MediaPage;