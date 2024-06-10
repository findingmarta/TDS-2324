import React, { useEffect, useState } from 'react';
import {StyleSheet, Text, Switch, View} from 'react-native';
import { useDispatch, useSelector } from "react-redux"
import { Divider } from '@rneui/themed';
import SelectDropdown from 'react-native-select-dropdown'

import { COLORS } from '../style/colors';

function Settings () {
    const dispatch = useDispatch();
    //const preferences = useSelector((state) => state.user.preferences);


    const [isEnabled, setIsEnabled] = useState(false);

    const distances = [250,500,750,1000,2000]

    // ir buscar o valor à store
    const [selectedDistance, setSelectedDistance] = useState(distances[0]);

    function handleSendNotifications() {
        setIsEnabled(previousState => !previousState);
    }

    function handleDistance(distance) {
        //dispatch(updatePreferences(distance: distance));
        console.log(distance);
    }




    return (
        <View style={styles.container}>

            <View style={styles.container_line}>
                <Text style={styles.text}>Send Notifications</Text>
                <Switch
                    trackColor={{false: COLORS.dark_gray, true:COLORS.logo_yellow}}
                    thumbColor={isEnabled ? COLORS.light_yellow : COLORS.light_gray}
                    onValueChange={handleSendNotifications}
                    value={isEnabled}/>
            </View>

            <Divider margin={20} width={1} color={COLORS.light_gray} />

            <View style={styles.container_line}>
                <Text style={styles.text}>Distance (m):</Text>
                
                <SelectDropdown
                    data={distances}
                    onSelect={(selectedItem, index) => {handleDistance(selectedItem)}}
                    renderButton={(selectedItem) => {
                        return (
                            <View style={styles.dropdownButtonStyle}>
                                <Text style={styles.dropdownButtonTxtStyle}>
                                    {selectedDistance || distances[0]}
                                </Text>
                            </View>
                        )
                    }}

                    renderItem={(item, index, isSelected) => {
                        return (
                            <View style={{...styles.dropdownItemStyle, ...(isSelected && {backgroundColor: '#D2D9DF'})}}>
                              <Text style={styles.dropdownItemTxtStyle}>{item}</Text>
                            </View>
                        )
                    }}
                />
            </View>

        </View>
    )

}

export default Settings;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 30,        
    },

    container_line: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        margin: 20,
    },

    text:{
        fontSize: 20,
        color: COLORS.dark_gray
    },

    dropdownButtonStyle: {
        width: 100,
        height: 50,
        backgroundColor: '#E9ECEF',
        borderRadius: 12,
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: 12,
    },
    
    dropdownButtonTxtStyle: {
        flex: 1,
        fontSize: 18,
        fontWeight: '500',
        color: '#151E26',
    },
    
    dropdownItemStyle: {
        width: '100%',
        flexDirection: 'row',
        paddingHorizontal: 12,
        justifyContent: 'center',
        alignItems: 'center',
        paddingVertical: 8,
    },

    dropdownItemTxtStyle: {
        flex: 1,
        fontSize: 18,
        fontWeight: '500',
        color: '#151E26',
    },
});
