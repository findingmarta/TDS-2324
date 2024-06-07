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
  points: [],
  status: "idle",
  error: null,
}

function getPoints (edges) {
  const points = [];
  const ids = [];

  edges.map((edge) => {
      const start = edge.edge_start;
      const end = edge.edge_end; 

      if (!ids.includes(start.id)){
            start['trail_id'] = edge.edge_trail
            points.push(start);
          ids.push(start.id);
      }


      if (!ids.includes(end.id)){
        end['trail_id'] = edge.edge_trail
        points.push(end);
        ids.push(end.id);
      }
  })

  return points;
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

            for (let i = 0; i < data.length; i++){
                const points = getPoints(data[i].edges);

                // Add the points to the state if they are not already there
                for (let j = 0; j < points.length; j++){
                    if (!state.points.find(point => point.id === points[j].id)){
                        state.points.push(points[j]);
                    }
                }
                console.log(state.points);
            }

            state.error = null;
        })
        .addCase(fetchTrailsData.rejected, (state, action) => {
            state.status = 'failed';
            state.error = action.error;
        });
    }
});

export default trailsSlice.reducer;