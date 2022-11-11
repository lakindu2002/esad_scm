import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { API_BASE_URL } from "../constants";
import { AppThunk } from "../store";
import { Product } from "../types/product";
import axiosInstance from "../util/axios";

interface ProductsState {
  products: Product[];
}

const initialState: ProductsState = {
  products: [],
};

const slice = createSlice({
  name: "products",
  initialState,
  reducers: {
    setAllProducts: (
      state: ProductsState,
      action: PayloadAction<Product[]>
    ): void => {
      state.products = action.payload;
    },
    updateProduct: (
      state: ProductsState,
      action: PayloadAction<{ product: Product; id: number }>
    ): void => {
      const index = state.products.findIndex(
        (product) => product.id === action.payload.id
      );
      state.products[index] = action.payload.product;
    },
    addProduct: (
      state: ProductsState,
      action: PayloadAction<Partial<Product>>
    ): void => {
      state.products.push(action.payload as Product);
    }
  },
});

export const getAllProducts =
  (): AppThunk =>
    async (dispatch): Promise<void> => {
      const resp = await axiosInstance.get<Product[]>(`${API_BASE_URL}/products`);
      dispatch(slice.actions.setAllProducts(resp.data));
    };

export const updateProduct =
  (patchAttr: Partial<Product>, id: number): AppThunk =>
    async (dispatch): Promise<void> => {
      const resp = await axiosInstance.patch<Product>(
        `${API_BASE_URL}/products/${id}`,
        patchAttr
      );
      dispatch(slice.actions.updateProduct({ product: resp.data, id }));
    };

export const createProduct = (product: Partial<Product>): AppThunk => async (dispatch): Promise<void> => {
  const resp = await axiosInstance.post<Product>(`${API_BASE_URL}/products/create`, product);
  dispatch(slice.actions.addProduct(resp.data));
}
export const { reducer } = slice;
