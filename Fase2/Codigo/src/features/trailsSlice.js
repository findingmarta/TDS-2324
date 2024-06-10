import { createSlice,createAsyncThunk } from '@reduxjs/toolkit';

// Here we use a Thunk to fetch data from an API. Thunks are used to handle async operations in Redux.
export const fetchTrailsData = createAsyncThunk('app/fetchTrailsData', async () => {
  try{
    console.log('Fetching Trails data...');
    const response = await fetch('https://39b6-193-137-92-72.ngrok-free.app/trails');
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching Trails data: ', error);
  }
});

const initialState = {
  trails: [],
  status: "idle",
  error: null,
}

const trailsSlice = createSlice({
    name: 'trails',
    initialState: initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
        .addCase(fetchTrailsData.pending, (state) => {
            state.status = 'loading';
            state.error = null;
        })
        .addCase(fetchTrailsData.fulfilled, (state, action) => {
            data = action.payload;
        
            state.status = 'succeeded';
            state.trails = data;
            state.error = null;
        })
        .addCase(fetchTrailsData.rejected, (state, action) => {
            state.status = 'failed';
            state.error = action.error;
        });
    }
});

export default trailsSlice.reducer;