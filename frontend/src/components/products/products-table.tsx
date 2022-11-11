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
  Typography,
} from "@mui/material";
import { FC } from "react";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import { Product } from "../../types/product";

interface ProductsTableProps {
  products: Product[];
  onEditClick: (materialToEdit: number) => void;
}

export const ProductsTable: FC<ProductsTableProps> = (props) => {
  const { products, onEditClick } = props;
  if (products.length === 0) {
    return (
      <Alert severity="info">
        There are no products available. Create one.
      </Alert>
    );
  }
  return (
    <TableContainer>
      <Table sx={{ minWidth: "100%" }}>
        <TableHead>
          <TableRow>
            <TableCell>
              <b>Product</b>
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
            <TableCell>
              <b>Raw Materials Used</b>
            </TableCell>
            <TableCell align="right">
              <b>Actions</b>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {products.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.name}</TableCell>
              <TableCell>{item.quantity}</TableCell>
              <TableCell>{item.thresholdQuantity}</TableCell>
              <TableCell>{item.price}</TableCell>
              <TableCell>
                {item.rawMaterials.map((rawMaterial) => (
                  <Typography
                    variant="body2"
                    key={rawMaterial.id}
                  >
                    {rawMaterial.rawMaterial.name}: {rawMaterial.quantityOfRawMaterialUsed}
                  </Typography>
                ))}

              </TableCell>
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
