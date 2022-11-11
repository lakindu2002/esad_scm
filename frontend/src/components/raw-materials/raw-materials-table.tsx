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
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import { RawMaterial } from "../../types/raw-material";

interface RawMaterialTableProps {
  rawMaterials: RawMaterial[];
  onEditClick: (materialToEdit: number) => void;
}

export const RawMaterialTable: FC<RawMaterialTableProps> = (props) => {
  const { rawMaterials, onEditClick } = props;
  if (rawMaterials.length === 0) {
    return (
      <Alert severity="info">
        There are no raw materials available. Create one.
      </Alert>
    );
  }
  return (
    <TableContainer>
      <Table sx={{ minWidth: "100%" }}>
        <TableHead>
          <TableRow>
            <TableCell>
              <b>Raw Material</b>
            </TableCell>
            <TableCell>
              <b>Supplier</b>
            </TableCell>
            <TableCell>
              <b>Quantity Available</b>
            </TableCell>
            <TableCell>
              <b>Threshold Level</b>
            </TableCell>
            <TableCell>
              <b>Unit Price (LKR)</b>
            </TableCell>
            <TableCell align="right">
              <b>Actions</b>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rawMaterials.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.name}</TableCell>
              <TableCell>{item.supplier?.name || "Not Available"}</TableCell>
              <TableCell>{item.quantity}</TableCell>
              <TableCell>{item.thresholdQuantity}</TableCell>
              <TableCell>{item.price}</TableCell>
              <TableCell align="right">
                <Box
                  sx={{
                    display: "flex",
                    alignItems: "center",
                    gap: 1,
                    justifyContent: "flex-end",
                  }}
                >
                  <IconButton onClick={() => onEditClick(item.id)}>
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
