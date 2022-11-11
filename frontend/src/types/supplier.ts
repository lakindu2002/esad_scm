import { RawMaterial } from "./raw-material"

export interface Supplier {
    id: number
    name: string
    email: string
    managingRawMaterials: RawMaterial[]
}