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

interface CreateEditSupplierProps {
  // pass undefined or leave empty to enforce create mode
  supplier?: Supplier;
  open: boolean;
  onClose: () => void;
}

export const CreateEditSupplier: FC<CreateEditSupplierProps> = (props) => {
  const { supplier, onClose, open } = props;
  const editMode = useMemo(() => !!supplier, [supplier]);
  const dispatch = useDispatch();

  const formik = useFormik({
    initialValues: {
      name: supplier?.name || "",
      email: supplier?.email || "",
    },
    validationSchema: Yup.object().shape({
      name: Yup.string()
        .required("Supplier name is required"),
      email: Yup.string()
        .email("Supplier email is poorly formatted")
        .required("Supplier email is required")
    }),
    onSubmit: async (values) => {
      try {
        const attr: Partial<Supplier> = { ...values };
        if (!editMode) {
          await dispatch(createSupplier(attr));
          toast.success("The supplier was created successully");
        } else {
          await dispatch(updateSupplier(attr, supplier?.id as number));
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
            {editMode ? "Edit Supplier" : "Create Supplier"}
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
              label="Email"
              size="medium"
              name="email"
              fullWidth
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={Boolean(formik.errors.email && formik.touched.email)}
              helperText={formik.errors.email}
              value={formik.values.email}
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
