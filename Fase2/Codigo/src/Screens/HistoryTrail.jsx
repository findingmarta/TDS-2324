import {Image, Linking, StyleSheet, Text, View,TouchableHighlight} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';

import HistoryTrailItem from '../Components/HistoryTrailItem';

import { COLORS } from '../style/colors';

function HistoryTrail() {
    // GET HISTORY


    // DEFINE BUTTON'S STATE

    return (
        <View style={styles.container}>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.history_container}>
                    <TouchableHighlight style={styles.historyButton}>
                        <Image source={require('../images/trail_logo.png')} style={styles.historyImage}/>
                    </TouchableHighlight>

                    <TouchableHighlight style={styles.historyButton}>
                        <Image source={require('../images/point_logo.png')} style={styles.historyImage}/>
                    </TouchableHighlight>
                </View>

                <View>
                    <HistoryTrailItem/>
                </View>
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'column',
        padding: 10,
    },

    history_container: {
        flexDirection: 'row',
        alignSelf:'center'
    },

    historyButton:{
        backgroundColor: COLORS.logo_yellow,
        margin:1,
        marginTop:10,
        borderRadius: 10,
        paddingVertical:10,
        paddingHorizontal:15,        
    },

    historyImage:{
        height: 30,
        width: 25,
    }
});

export default HistoryTrail;