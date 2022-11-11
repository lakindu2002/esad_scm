import { useFormik } from "formik";
import { FC, useEffect, useMemo } from "react";
import { useDispatch, useSelector } from "../../store";
import * as Yup from "yup";
import LoadingButton from "@mui/lab/LoadingButton";
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  MenuItem,
  TextField,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import toast from "react-hot-toast";
import { mapAxiosError } from "../../util/map-axios-error";
import { RawMaterial } from "../../types/raw-material";
import { createRawMaterial, updateRawMaterial } from "../../slices/raw-materials";
import { getAllSuppliers } from "../../slices/suppliers";

interface CreateEditRawMaterialProps {
  // pass undefined or leave empty to enforce create mode
  rawMaterial?: RawMaterial;
  open: boolean;
  onClose: () => void;
}

export const CreateEditRawMaterial: FC<CreateEditRawMaterialProps> = (props) => {
  const { rawMaterial, onClose, open } = props;
  const editMode = useMemo(() => !!rawMaterial, [rawMaterial]);
  const dispatch = useDispatch();
  const { suppliers } = useSelector((state) => state.suppliers);

  useEffect(() => {
    // get suppliers for assigning when editing or creating new item.
    dispatch(getAllSuppliers());
  }, [dispatch]);

  const formik = useFormik({
    initialValues: {
      name: rawMaterial?.name || "",
      quantity: rawMaterial?.quantity || 0,
      price: rawMaterial?.price || 0,
      thresholdQuantity: rawMaterial?.thresholdQuantity || 0,
      supplier: rawMaterial?.supplier?.id || undefined,
    },
    validationSchema: Yup.object().shape({
      name: Yup.string()
        .required("Item name is required"),
      quantity: Yup.number().typeError("Quantity must be a number").min(1, "Minimum of 1 should be added").required("Quantity is required"),
      price: Yup.number().typeError("Price must be a number").min(1, "Price should be be greater than 1 LKR").required("Price is required"),
      thresholdQuantity: Yup.number().typeError("Threshold quantity must be a number").min(1, "Threshold cannot be less than 1").required("Threshold quantity is required"),
      supplier: Yup.number().typeError("Supplier is required").required("Please specify the supplier providing this item"),
    }),
    onSubmit: async (values) => {
      try {
        const selectedSupplier = suppliers.find((supplier) => supplier.id === values.supplier);
        const attr: Partial<RawMaterial> = { ...values, supplier: selectedSupplier };
        if (!editMode) {
          await dispatch(createRawMaterial(attr));
          toast.success("Raw material created successfully");
        } else {
          await dispatch(updateRawMaterial(attr, rawMaterial?.id as number))
          toast.success("Raw material updated successfully");
        }
        onClose();
      } catch (err) {
        toast.error(mapAxiosError(err));
      }
    },
  });

  return (

    <Dialog open={open} onClose={() => onClose()} maxWidth="md">
      <DialogTitle>
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
          }}
        >
          <Typography variant="h6">
            {editMode ? "Edit Raw Material" : "Add Raw Material"}
          </Typography>
          <IconButton onClick={() => onClose()}>
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form
        noValidate
        onSubmit={formik.handleSubmit}
      >
        <DialogContent>
          <TextField
            label="Name"
            size="medium"
            name="name"
            fullWidth
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            margin="normal"
            error={Boolean(formik.errors.name && formik.touched.name)}
            helperText={formik.errors.name}
            value={formik.values.name}
          />
          <TextField
            label="Quantity"
            size="medium"
            name="quantity"
            fullWidth
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            margin="normal"
            error={Boolean(formik.errors.quantity && formik.touched.quantity)}
            helperText={formik.errors.quantity}
            value={formik.values.quantity}
          />
          <TextField
            label="Price"
            InputProps={{
              startAdornment: (
                <Typography sx={{
                  mr: 1
                }}
                  color="text.secondary"
                >
                  LKR
                </Typography>
              )
            }}
            size="medium"
            name="price"
            fullWidth
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            margin="normal"
            error={Boolean(formik.errors.price && formik.touched.price)}
            helperText={formik.errors.price}
            value={formik.values.price}
          />
          <TextField
            label="Threshold Quantity"
            size="medium"
            name="thresholdQuantity"
            fullWidth
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            margin="normal"
            error={Boolean(formik.errors.thresholdQuantity && formik.touched.thresholdQuantity)}
            helperText={formik.errors.thresholdQuantity}
            value={formik.values.thresholdQuantity}
          />
          <TextField
            select
            margin="normal"
            value={formik.values.supplier}
            size="medium"
            name="supplier"
            fullWidth
            label={"Supplier"}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            error={Boolean(formik.errors.supplier && formik.touched.supplier)}
            helperText={formik.errors.supplier}
          >
            {suppliers.map((supplier) => (
              <MenuItem value={supplier.id}
                key={supplier.id}
              >
                {supplier.name}
              </MenuItem>
            ))}
          </TextField>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => onClose()}
            color="error"
            type="button"
          >
            {editMode ? "Cancel" : "Close"}
          </Button>
          <LoadingButton
            type="submit"
            variant="contained"
            loading={formik.isSubmitting}
          >
            {editMode ? "Update" : "Create"}
          </LoadingButton>
        </DialogActions>
      </form>
    </Dialog>
  );
};
