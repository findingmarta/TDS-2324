import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    historyTrail: {
        trails: [/*
            id: '',                  // how to increment this?
            difficulty: '',
            travelled_distance: '',
            travelled_time: ''
        */],
        
    },
    historyPoint: {
        points: [/*
            id: '',                  // how to increment this?
            name: '',
        */],
    },

    status: "idle",
    error: null,
  }
  
  
  const historyTrailSlice = createSlice({
    name: 'historyTrail',
    initialState: initialState,
    reducers: {
      // Here we can define additional actions to modify the state.
      addTrail: (state, action) => {
        const new_trail = {
            id: action.payload.id,
            name: action.payload.name,
            date: action.payload.date,
            duration: action.payload.duration,
            difficulty: action.payload.difficulty,
            travelled_distance: action.payload.travelled_distance,
            travelled_time: action.payload.travelled_time,
        };
        console.log('new_trail', new_trail);
        state.historyTrail.trails.push(new_trail);
      },

      updateTrail: (state, action) => {
        console.log('updateTrail', action.payload);
        const {id, date, travelled_distance, travelled_time} = action.payload;
        const existingTrail = state.historyTrail.trails.find(trail => trail.id === id);
        if (existingTrail) {
            existingTrail.date = date;
            existingTrail.travelled_distance = travelled_distance;
            existingTrail.travelled_time = travelled_time;
        }
      },

      addPoint: (state, action) => {
        const new_point = {
            id: action.payload.id,
            name: action.payload.name,
        };
        console.log('new_point', new_point);
        state.historyPoint.points.push(new_point);
      }
    },
  });

  export const { addTrail, updateTrail, addPoint } = historyTrailSlice.actions;
  export default historyTrailSlice.reducer;