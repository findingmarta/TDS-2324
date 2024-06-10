import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  trails: [
    // id: '',    
    // name: '',
    // date: '',
    // duration: '',     
    // difficulty: '',
    // travelled_distance: '',
    // travelled_time: ''
  ],

  points: [
    //id: '',                  
    //name: '',
    //desc: ''
  ],
    
  status: "idle",
  error: null,
}
  
  
const historyTrailSlice = createSlice({
  name: 'history',
  initialState: initialState,
  reducers: {
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
      state.trails.push(new_trail);
    },

    updateTrail: (state, action) => {
      const id = action.payload.id;
      const existingTrail = state.trails.find(trail => trail.id === id);

      existingTrail.date = action.payload.date;
      existingTrail.travelled_distance = action.payload.travelled_distance;
      existingTrail.travelled_time = action.payload.travelled_time;
    },

    addPoint: (state, action) => {
      const new_point = {
          id: action.payload.id,
          pin_name: action.payload.pin_name,
          pin_desc: action.payload.pin_desc,
          media: action.payload.media,
          pin_alt: action.payload.pin_alt,
          pin_lat: action.payload.pin_lat,
          pin_lng: action.payload.pin_lng,
          rel_pin: action.payload.rel_pin
      };
      state.points.push(new_point);
    }
  },
});

export const { addTrail, updateTrail, addPoint } = historyTrailSlice.actions;
export default historyTrailSlice.reducer;