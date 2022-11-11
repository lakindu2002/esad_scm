import {
    Alert,
    Box,
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
  } from "@mui/material";
  import { FC } from "react";
  import { Supplier } from "../../types/supplier";
  import DeleteIcon from "@mui/icons-material/Delete";
  import ModeEditIcon from "@mui/icons-material/ModeEdit";
import { Vehicle } from "../../types/vehicle";
  
  interface VehicleTableProps {
    vehicles: Vehicle[];
    onEditClick: (vehicleIdToEdit: number) => void;
  }
  
  export const VehicleTable: FC<VehicleTableProps> = (props) => {
    const { vehicles, onEditClick } = props;
    if (vehicles.length === 0) {
      return (
        <Alert severity="info">
          There are no vehicles available. Create one.
        </Alert>
      );
    }
    return (
      <TableContainer>
        <Table sx={{ minWidth: "100%" }}>
          <TableHead>
            <TableRow>
              <TableCell>
                <b>Name</b>
              </TableCell>
              <TableCell>
                <b>License Plate</b>
              </TableCell>
              <TableCell>
                <b>Chassis Number</b>
              </TableCell>
              <TableCell>
                <b>Maximum Packages</b>
              </TableCell>
              <TableCell align="right">
                <b>Actions</b>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {vehicles.map((vehicle) => (
              <TableRow key={vehicle.id}>
                <TableCell>{vehicle.name}</TableCell>
                <TableCell>{vehicle.licensePlate.toUpperCase()}</TableCell>
                <TableCell>{vehicle.chassisNumber}</TableCell>
                <TableCell>{vehicle.maxPackageCapacity}</TableCell>
                <TableCell align="right">
                  <Box
                    sx={{
                      display: "flex",
                      alignItems: "center",
                      gap: 1,
                      justifyContent: "flex-end",
                    }}
                  >
                    <IconButton onClick={() => onEditClick(vehicle.id)}>
                      <ModeEditIcon />
                    </IconButton>
                  </Box>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  };
  