import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { API_BASE_URL } from "../constants";
import { AppThunk } from "../store";
import { Supplier } from "../types/supplier";
import axiosInstance from "../util/axios";

interface SuppliersState {
  suppliers: Supplier[];
}

const initialState: SuppliersState = {
  suppliers: [],
};

const slice = createSlice({
  name: "suppliers",
  initialState,
  reducers: {
    setSuppliers(
      state: SuppliersState,
      action: PayloadAction<Supplier[]>
    ): void {
      state.suppliers = action.payload;
    },
    addSupplier(state: SuppliersState, action: PayloadAction<Supplier>): void {
      state.suppliers.push(action.payload);
    },
    patchSupplier(
      state: SuppliersState,
      action: PayloadAction<{ supplier: Supplier; id: number }>
    ): void {
      const index = state.suppliers.findIndex(
        (supplier) => supplier.id === action.payload.id
      );
      state.suppliers[index] = action.payload.supplier;
    },
    removeSupplier(state: SuppliersState, action: PayloadAction<number>): void {
      state.suppliers = state.suppliers.filter(
        (supplier) => supplier.id !== action.payload
      );
    }
  },
});

export const getAllSuppliers =
  (): AppThunk =>
    async (dispatch): Promise<void> => {
      dispatch(slice.actions.setSuppliers([]));
      const resp = await axiosInstance.get<Supplier[]>(`${API_BASE_URL}/suppliers/`);
      dispatch(slice.actions.setSuppliers(resp.data));
    };

export const createSupplier =
  (supplier: Partial<Supplier>): AppThunk =>
    async (dispatch): Promise<void> => {
      const resp = await axiosInstance.post<Supplier>(
        `${API_BASE_URL}/suppliers/create`,
        supplier
      );
      dispatch(slice.actions.addSupplier(resp.data));
    };

export const updateSupplier =
  (supplier: Partial<Supplier>, id: number): AppThunk =>
    async (dispatch): Promise<void> => {
      const resp = await axiosInstance.patch<Supplier>(
        `${API_BASE_URL}/suppliers/${id}`,
        supplier
      );
      dispatch(slice.actions.patchSupplier({ supplier: resp.data, id }));
    };

export const deleteSupplier = (id: number): AppThunk => async (dispatch): Promise<void> => {
  await axiosInstance.delete(`${API_BASE_URL}/suppliers/${id}`);
  dispatch(slice.actions.removeSupplier(id));
};

export const { reducer } = slice;
