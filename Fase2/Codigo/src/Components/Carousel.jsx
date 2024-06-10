import React, { useState, useRef } from 'react';
import { Image, StyleSheet, View, TouchableOpacity, FlatList, Dimensions } from 'react-native';
import { COLORS } from '../style/colors';
import Video from 'react-native-video';
const SCREEN_WIDTH = Dimensions.get('window').width;

function Carousel({ data }) {
    const flatlistRef = useRef(null);
    const [slide, setSlide] = useState(0); // Start from 0
    const [prevDisabled, setPrevDisabled] = useState(true);
    const [nextDisabled, setNextDisabled] = useState(false);
    const [isPlaying, setIsPlaying] = React.useState(false); 


    const onPrevious = () => {
        if (slide === 0) {
            //setPrevDisabled(true); 
            return;
        }
        if (flatlistRef.current) {
            flatlistRef.current.scrollToIndex({ index: slide - 1 });
            setSlide(slide - 1);
        }
        setIsPlaying(false)
    };

    const onNext = () => {
        if (slide === data.length - 1) {
            return;
        }
        if (flatlistRef.current) {
            flatlistRef.current.scrollToIndex({ index: slide + 1 });
            setSlide(slide + 1);
        }
    };
    
    return (
        <View>
            <FlatList
                ref={flatlistRef}
                horizontal
                showsHorizontalScrollIndicator={false}
                snapToInterval={SCREEN_WIDTH}
                snapToAlignment="center"
                decelerationRate={'fast'}
                pagingEnabled={true}
                onScroll={props => {
                    const offset = props.nativeEvent.contentOffset.x / SCREEN_WIDTH;
                    const newSlide = Math.round(offset);
                    if (newSlide !== slide) {
                        setSlide(newSlide);
                        setPrevDisabled(newSlide === 0);
                        setNextDisabled(newSlide === data.length - 1);
                        setIsPlaying(false)
                    }
                }}
                data={data}
                keyExtractor={item => item.id.toString()}
                renderItem={({ item }) => {
                    return (
                        <View style={styles.cont}>
                            { item.type === 'V' && (
                                <TouchableOpacity
                                style={styles.button_video}
                                onPress={() => setIsPlaying(p => !p)}>
                                    <Video  
                                        source={{uri : item.uri}}      // the video file
                                        paused={isPlaying}                  // make it start    
                                        style={styles.video}            // any style you want
                                        repeat={true}                   // make it a loop
                                    />
                                </TouchableOpacity>)}
                            { item.type === 'I' && (
                            <Image source={{ uri: item.uri }} style={styles.image}></Image>)}
                        </View>
                    );
                }}
            />
            <View style={styles.container_desc}>
                {data.length > 1 && (
                    <TouchableOpacity
                        style={styles.button_media}
                        onPress={onPrevious}
                        disabled={prevDisabled}
                    >
                        <Image source={require('../images/left_arrow2.png')} style={[styles.image_media,{tintColor : prevDisabled ? COLORS.light_blue: COLORS.white}]} />
                    </TouchableOpacity>
                )}
                {data.length > 1 && (
                    <TouchableOpacity
                        style={styles.button_media}
                        onPress={onNext}
                        disabled={nextDisabled}
                    >
                        <Image source={require('../images/right_arrow2.png')} style={[styles.image_media,{tintColor : nextDisabled ? COLORS.light_blue: COLORS.white}]} />
                    </TouchableOpacity>
                )}
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    cont: {
        width: SCREEN_WIDTH,
        justifyContent: 'center',
        alignItems: 'center',
    },
    image: {
        width: '70%',
        alignSelf: 'center',
        height: 350,
    },
    container_desc: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginBottom: 30,
    },
    button_media: {
        marginLeft: 40,
        marginRight: 50,
        marginTop: 20,
        width: 30,
        height: 30,
    },
    image_media: {
        height: 30,
        width: 30,
    },

    video:{
        width: "80%",
        height: 200,
        alignSelf: 'center',
        margin: 20
    },

    button_video:{
        width: "100%",
        height: 200,
        alignSelf: 'center',
        margin: 20
    },
});

export default Carousel;