import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { API_BASE_URL } from "../constants";
import { AppThunk } from "../store";
import { Vehicle, VehicleType } from "../types/vehicle";
import axiosInstance from "../util/axios";

interface VehicleState {
  vehicles: Vehicle[];
}

const initialState: VehicleState = {
  vehicles: [],
};

const slice = createSlice({
  name: "vehicles",
  initialState,
  reducers: {
    setVehicles: (state: VehicleState, action: PayloadAction<Vehicle[]>) => {
      state.vehicles = action.payload;
    },
    addVehicle: (state: VehicleState, action: PayloadAction<Vehicle>) => {
      state.vehicles.push(action.payload);
    },
    updateVehicle: (state: VehicleState, action: PayloadAction<{ vehicle: Vehicle, id: number }>) => {
      const index = state.vehicles.findIndex((vehicle) => vehicle.id === action.payload.id);
      state.vehicles[index] = action.payload.vehicle;
    }
  },
});

export const { reducer } = slice;

export const getVehicles = (type: VehicleType): AppThunk => async dispatch => {
  const response = await axiosInstance.get<Vehicle[]>(`${API_BASE_URL}/vehicles/${type}s`);
  dispatch(slice.actions.setVehicles(response.data));
}

export const createVehicle = (vehicle: Partial<Vehicle>, type: VehicleType): AppThunk => async dispatch => {
  const response = await axiosInstance.post<Vehicle>(`${API_BASE_URL}/vehicles/${type}s/create`, vehicle);
  dispatch(slice.actions.addVehicle(response.data));
}

export const updateVehicle = (vehicle: Partial<Vehicle>, type: VehicleType, id: number): AppThunk => async dispatch => {
  const response = await axiosInstance.patch<Vehicle>(`${API_BASE_URL}/vehicles/${type}s/${id}`, vehicle);
  dispatch(slice.actions.updateVehicle({ vehicle: response.data, id }));
}