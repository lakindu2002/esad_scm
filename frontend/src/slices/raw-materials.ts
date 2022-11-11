import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { API_BASE_URL } from "../constants";
import { AppThunk } from "../store";
import { RawMaterial } from "../types/raw-material";
import axiosInstance from "../util/axios";

interface RawMaterialsState {
    rawMaterials: RawMaterial[]
}

const initialState: RawMaterialsState = {
    rawMaterials: []
}

const slice = createSlice({
    name: 'rawMaterials',
    initialState,
    reducers: {
        setAllRawMaterials: (state: RawMaterialsState, action: PayloadAction<RawMaterial[]>): void => {
            state.rawMaterials = action.payload
        },
        addRawMaterial: (state: RawMaterialsState, action: PayloadAction<RawMaterial>): void => {
            state.rawMaterials = [...state.rawMaterials, action.payload];
        },
        updateRawMaterial: (state: RawMaterialsState, action: PayloadAction<{ rawMaterial: RawMaterial, id: number }>): void => {
            const index = state.rawMaterials.findIndex(rawMaterial => rawMaterial.id === action.payload.id);
            state.rawMaterials[index] = action.payload.rawMaterial;
        }
    }
});

export const getAllRawMaterials = (): AppThunk => async (dispatch): Promise<void> => {
    const resp = await axiosInstance.get<RawMaterial[]>(`${API_BASE_URL}/raw-materials`);
    dispatch(slice.actions.setAllRawMaterials(resp.data));
};

export const createRawMaterial = (rawMaterial: Partial<RawMaterial>): AppThunk => async (dispatch): Promise<void> => {
    const resp = await axiosInstance.post<RawMaterial>(`${API_BASE_URL}/raw-materials/create`, rawMaterial);
    dispatch(slice.actions.addRawMaterial(resp.data));
};

export const updateRawMaterial = (patchAttr: Partial<RawMaterial>, id: number): AppThunk => async (dispatch): Promise<void> => {
    const resp = await axiosInstance.patch<RawMaterial>(`${API_BASE_URL}/raw-materials/${id}`, patchAttr);
    dispatch(slice.actions.updateRawMaterial({ rawMaterial: resp.data, id }));
}

export const sortItemsByThreshold = (direction: 'ascending' | 'descending'): AppThunk => async (dispatch, getState): Promise<void> => {
    const resp = await axiosInstance.get<RawMaterial[]>(`${API_BASE_URL}/raw-materials/sort/${direction}`);
    dispatch(slice.actions.setAllRawMaterials(resp.data));
}
export const { reducer } = slice;