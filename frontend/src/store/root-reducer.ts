import { combineReducers } from "@reduxjs/toolkit";
import { reducer as productsReducer } from "../slices/products";
import { reducer as rawMaterialsReducer } from "../slices/raw-materials";
import { reducer as suppliersReducer } from "../slices/suppliers";
import { reducer as vehiclesReducer } from "../slices/vehicles";


export const rootReducer = combineReducers({
  products: productsReducer,
  rawMaterials: rawMaterialsReducer,
  suppliers: suppliersReducer,
  vehicles: vehiclesReducer
});
