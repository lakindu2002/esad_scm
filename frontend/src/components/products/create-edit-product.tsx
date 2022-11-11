import { useFormik } from "formik";
import { FC, useEffect, useMemo, useState } from "react";
import { useDispatch, useSelector } from "../../store";
import * as Yup from "yup";
import LoadingButton from "@mui/lab/LoadingButton";
import {
  Box,
  Button,
  Card,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Step,
  StepLabel,
  Stepper,
  TextField,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import toast from "react-hot-toast";
import { mapAxiosError } from "../../util/map-axios-error";
import { Product } from "../../types/product";
import { getAllRawMaterials } from "../../slices/raw-materials";
import { RawMaterial, RawMaterialProduct } from "../../types/raw-material";
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import { createProduct, updateProduct } from "../../slices/products";

interface CreateEditProductProps {
  // pass undefined or leave empty to enforce create mode
  product?: Product;
  open: boolean;
  onClose: () => void;
}


export const CreateEditProduct: FC<CreateEditProductProps> = (props) => {
  const { product, onClose, open } = props;
  const editMode = useMemo(() => !!product, [product]);
  const dispatch = useDispatch();
  const { rawMaterials } = useSelector((state) => state.rawMaterials);
  const [currentStep, setCurrentStep] = useState<number>(0);
  const [productToCreate, setProductToCreate] = useState<Partial<Product> | undefined>(undefined);
  const [addedRawMaterials, setAddedRawMaterials] = useState<RawMaterialProduct[]>([]);

  const steps = useMemo(() => editMode ? ["Product Details"] : ["Product Details", "Raw Materials"], [editMode]);

  useEffect(() => {
    dispatch(getAllRawMaterials());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const formik = useFormik({
    initialValues: {
      name: product?.name || "",
      quantity: product?.quantity || 0,
      price: product?.price || 0,
      thresholdQuantity: product?.thresholdQuantity || 0,
    },
    validationSchema: Yup.object().shape({
      name: Yup.string()
        .required("Item name is required"),
      quantity: Yup.number().typeError("Quantity must be a number").min(1, "Minimum of 1 should be added").required("Quantity is required"),
      price: Yup.number().typeError("Price must be a number").min(1, "Price should be be greater than 1 LKR").required("Price is required"),
      thresholdQuantity: Yup.number().typeError("Threshold quantity must be a number").min(1).required("Threshold quantity is required"),
    }),
    onSubmit: async (values) => {
      setProductToCreate({ ...values as Partial<Product> });
      setCurrentStep(1);
    },
  });

  const handleCreateProduct = async () => {
    try {
      const postAttribute: Partial<Product> = {
        ...productToCreate as Partial<Product>,
        rawMaterials: addedRawMaterials,
      }
      await dispatch(createProduct(postAttribute));
      toast.success("Product created successfully");
      onClose();
    } catch (err) {
      toast.error(mapAxiosError(err));
    }
  };

  const handleAddQuantity = (rawMaterialId: number) => {
    const addedRawMaterial: RawMaterialProduct | undefined = addedRawMaterials.find((rawMaterial) => rawMaterial.rawMaterial.id === rawMaterialId);
    if (addedRawMaterial) {
      addedRawMaterial.quantityOfRawMaterialUsed += 1;
      setAddedRawMaterials([...addedRawMaterials]);
    } else {
      const rawMaterial: RawMaterial = rawMaterials.find((rawMaterial) => rawMaterial.id === rawMaterialId) as RawMaterial;
      const newRawMaterial: Partial<RawMaterialProduct> = {
        rawMaterial,
        quantityOfRawMaterialUsed: 1,
      }
      setAddedRawMaterials([...addedRawMaterials, newRawMaterial as RawMaterialProduct]);
    }
  }

  const handleReduceQuantity = (rawMaterialId: number) => {
    const addedRawMaterial: RawMaterialProduct | undefined = addedRawMaterials.find((rawMaterial) => rawMaterial.rawMaterial.id === rawMaterialId);
    if (addedRawMaterial) {
      if (addedRawMaterial.quantityOfRawMaterialUsed > 1) {
        addedRawMaterial.quantityOfRawMaterialUsed -= 1;
        setAddedRawMaterials([...addedRawMaterials]);
      } else {
        setAddedRawMaterials(addedRawMaterials.filter((rawMaterial) => rawMaterial.rawMaterial.id !== rawMaterialId));
      }
    }
  }

  const handleUpdateProduct = async () => {
    try {
      const patchAttributes: Partial<Product> = { ...formik.values };
      await dispatch(updateProduct(patchAttributes, product?.id as number));
      toast.success("Product updated successfully");
      onClose();
    } catch (err) {
      toast.error(mapAxiosError(err));
    }
  }

  return (

    <Dialog open={open} onClose={() => onClose()} maxWidth="md" PaperProps={{
      sx: {
        width: '100%'
      }
    }}>
      <DialogTitle>
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
          }}
        >
          <Typography variant="h6">
            {editMode ? "Edit Product" : "Add Product"}
          </Typography>
          <IconButton onClick={() => onClose()}>
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <Stepper
        activeStep={currentStep}
        nonLinear={false}
        alternativeLabel
      >
        {steps.map((label) => (
          <Step
            key={label}
          >
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
      <DialogContent>
        {currentStep === 0 && (
          <>
            <form
              noValidate
              onSubmit={formik.handleSubmit}
            >
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
            </form>
          </>
        )}
        {currentStep === 1 && (
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, }}>
            {rawMaterials.map((rawMaterial) => (
              <Card
                key={rawMaterial.id}
              >
                <CardContent>
                  <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <Typography variant="body1">
                      {rawMaterial.name}
                    </Typography>
                    <Box sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
                      <IconButton onClick={() => handleAddQuantity(rawMaterial.id)}>
                        <AddIcon />
                      </IconButton>
                      <Typography>
                        {addedRawMaterials.find((addedRawMaterial) => addedRawMaterial.rawMaterial.id === rawMaterial.id)?.quantityOfRawMaterialUsed || 0}
                      </Typography>
                      <IconButton onClick={() => handleReduceQuantity(rawMaterial.id)}>
                        <RemoveIcon />
                      </IconButton>
                    </Box>
                  </Box>
                </CardContent>
              </Card>
            ))}
          </Box>
        )}
      </DialogContent>
      <DialogActions>
        <Button
          onClick={() => {
            if (currentStep === 0) {
              onClose();
            } else {
              setCurrentStep(0);
            }
          }}
          color="error"
          type="button"
        >
          {currentStep === 0 && editMode ? "Cancel" : "Back"}
        </Button>
        <LoadingButton
          variant="contained"
          disabled={currentStep === 0 && !formik.isValid || currentStep === 1 && addedRawMaterials.length === 0}
          onClick={() => {
            if (currentStep === 0 && !editMode) {
              formik.handleSubmit();
            } else {
              if (editMode) {
                handleUpdateProduct();
                return;
              }
              handleCreateProduct();
            }
          }}
        >
          {currentStep === 0 && !editMode ? "Next" : "Confirm"}
        </LoadingButton>
      </DialogActions>
    </Dialog>
  );
};
