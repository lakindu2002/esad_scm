import { Product } from "./product";
import { Supplier } from "./supplier";

export interface RawMaterial {
    id: number
    name: string
    quantity: number;
    price: number;
    thresholdQuantity: number;
    products: RawMaterialProduct[];
    supplier?: Supplier
}

export interface RawMaterialProduct {
    id: number;
    product: Product
    rawMaterial: RawMaterial
    quantityOfRawMaterialUsed: number;
}