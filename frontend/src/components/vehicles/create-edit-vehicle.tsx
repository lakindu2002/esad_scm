import { useFormik } from "formik";
import { FC, useMemo } from "react";
import { useDispatch } from "../../store";
import { Supplier } from "../../types/supplier";
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
  TextField,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { createSupplier, updateSupplier } from "../../slices/suppliers";
import toast from "react-hot-toast";
import { mapAxiosError } from "../../util/map-axios-error";
import { Vehicle, VehicleType } from "../../types/vehicle";
import { createVehicle, updateVehicle } from "../../slices/vehicles";

interface CreateEditVehicleProps {
  // pass undefined or leave empty to enforce create mode
  vehicle?: Vehicle;
  type: VehicleType
  open: boolean;
  onClose: () => void;
}

export const CreateEditVehicle: FC<CreateEditVehicleProps> = (props) => {
  const { vehicle, onClose, open, type } = props;
  const editMode = useMemo(() => !!vehicle, [vehicle]);
  const dispatch = useDispatch();

  const formik = useFormik({
    initialValues: {
      name: vehicle?.name || "",
      licensePlate: vehicle?.licensePlate || "",
      chassisNumber: vehicle?.chassisNumber || "",
      maxPackageCapacity: vehicle?.maxPackageCapacity || 0,
    },
    validationSchema: Yup.object().shape({
      name: Yup.string()
        .required("Vehicle name is required"),
      licensePlate: Yup.string()
        .required("License Plate is required"),
      chassisNumber: Yup.string()
        .required("Chassis Number is required"),
      maxPackageCapacity: Yup.number().typeError("Max Package Capacity must be a number")
        .required("Max Package Capacity is required"),
    }),
    onSubmit: async (values) => {
      try {
        const attr: Partial<Vehicle> = { ...values };
        if (!editMode) {
          await dispatch(createVehicle(attr, type));
          toast.success("The vehicle was created successully");
        } else {
          await dispatch(updateVehicle(attr, type, vehicle?.id as number));
          toast.success("The supplier information was updated successfully");
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
            {editMode ? `Edit ${type} ` : `Create ${type}`}
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
          <Box sx={{ my: 3 }}>
            <TextField
              label="Name"
              size="medium"
              name="name"
              fullWidth
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={Boolean(formik.errors.name && formik.touched.name)}
              helperText={formik.errors.name}
              sx={{ mb: 2 }}
              value={formik.values.name}
            />
            <TextField
              label="License Plate"
              size="medium"
              name="licensePlate"
              fullWidth
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              sx={{ mb: 2 }}
              error={Boolean(formik.errors.licensePlate && formik.touched.licensePlate)}
              helperText={formik.errors.licensePlate}
              value={formik.values.licensePlate}
            />
            <TextField
              label="Chassis Number"
              size="medium"
              name="chassisNumber"
              fullWidth
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              sx={{ mb: 2 }}
              error={Boolean(formik.errors.chassisNumber && formik.touched.chassisNumber)}
              helperText={formik.errors.chassisNumber}
              value={formik.values.chassisNumber}
            />
            <TextField
              label="Max Packkages"
              size="medium"
              name="maxPackageCapacity"
              fullWidth
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={Boolean(formik.errors.maxPackageCapacity && formik.touched.maxPackageCapacity)}
              helperText={formik.errors.maxPackageCapacity}
              value={formik.values.maxPackageCapacity}
            />
          </Box>
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
