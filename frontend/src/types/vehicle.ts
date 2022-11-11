export interface Vehicle {
  id: number;
  licensePlate: string;
  chassisNumber: string;
  maxPackageCapacity: number;
  name: string
}

export type VehicleType = 'car' | 'truck' | 'van';
