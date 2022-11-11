import { RawMaterialProduct } from "./raw-material";

export interface Product {
    id: number
    name: string
    quantity: number;
    price: number;
    thresholdQuantity: number;
    rawMaterials: RawMaterialProduct[];
}