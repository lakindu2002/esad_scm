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

interface SupplierTableProps {
  suppliers: Supplier[];
  onEditClick: (supplierIdToEdit: number) => void;
  onDeleteClick: (supplierIdToDelete: number) => void;
}

export const SupplierTable: FC<SupplierTableProps> = (props) => {
  const { suppliers, onDeleteClick, onEditClick } = props;
  if (suppliers.length === 0) {
    return (
      <Alert severity="info">
        There are no suppliers available. Create one.
      </Alert>
    );
  }
  return (
    <TableContainer>
      <Table sx={{ minWidth: "100%" }}>
        <TableHead>
          <TableRow>
            <TableCell>
              <b>Supplier</b>
            </TableCell>
            <TableCell>
              <b>Contact Information</b>
            </TableCell>
            <TableCell align="right">
              <b>Actions</b>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {suppliers.map((supplier) => (
            <TableRow key={supplier.id}>
              <TableCell>{supplier.name}</TableCell>
              <TableCell>{supplier.email}</TableCell>
              <TableCell align="right">
                <Box
                  sx={{
                    display: "flex",
                    alignItems: "center",
                    gap: 1,
                    justifyContent: "flex-end",
                  }}
                >
                  <IconButton onClick={() => onEditClick(supplier.id)}>
                    <ModeEditIcon />
                  </IconButton>
                  <IconButton onClick={() => onDeleteClick(supplier.id)}>
                    <DeleteIcon />
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
