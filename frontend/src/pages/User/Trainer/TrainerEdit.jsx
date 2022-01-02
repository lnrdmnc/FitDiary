import React, {useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem,
    Heading,
    Input,
    SimpleGrid,
    useBreakpointValue,
    VStack
} from "@chakra-ui/react";
import config from "../../../config.json";
import {getTrainer} from "../../../fakeBackend";

export default function TrainerEdit(props) {
    const urlEdit = `${config.SERVER_URL}/utenti/preparatore`;
    const {register, handleSubmit, setValue, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})

    function isValidDate(value) {
        return (!isNaN(Date.parse(value)) && (new Date(value) < Date.now()) ? true : "Inserisci una data valida");
    }

    const resp = getTrainer();

    useEffect(() => {
        const fields = ['nome', 'cognome', 'email', 'dataNascita', 'telefono', 'via', 'cap', 'citta'];
        fields.forEach(field => setValue(field, resp.data.utente[field]));
    })

    function handleResponse(response) {
        console.log("handling");
        return response.text().then(text => {
            const resp = JSON.parse(text);
            if (!response.ok) {
                const error = (resp && resp.message) || response.statusText;
                return Promise.reject(error);
            }
        });
    }

    //Gestione FAIL
    function handleFail(data) {
        console.log("something went wrong " + data);
    }

    function onSubmit(values) {
        console.log("submitting");
        const requestOptions = {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(values)
        }
        return fetch(urlEdit, requestOptions)
            .then(handleResponse)
            .catch(handleFail)
    }


    return (<VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="2xl">Modifica dati Personali</Heading>
            </VStack>
            <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">

                    <GridItem colSpan={colSpan} w="100%">
                        <FormControl id={"nome"} isInvalid={errors.nome}>
                            <FormLabel htmlFor="nome">Nome</FormLabel>
                            <Input type="text"  {...register("nome", {
                                maxLength: {
                                    value: 50, message: "Il nome è troppo lungo"
                                }, pattern: {value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i, message: "Formato nome non valido"}
                            })} />
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>

                    <GridItem>
                        <FormControl id={"cognome"} isInvalid={errors.cognome}></FormControl>
                        <FormLabel htmlFor="cognome">Cognome</FormLabel>
                        <Input type="text" {...register("cognome", {
                            maxLength: {
                                value: 50, message: "Il cognome è troppo lungo"
                            }, pattern: {value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i, message: "Formato cognome non valido"}
                        })} />
                        <FormErrorMessage>{errors.cognome && errors.cognome.message}</FormErrorMessage>
                    </GridItem>

                    <GridItem>
                        <FormControl id={"email"} isInvalid={errors.email}>
                            <FormLabel>Email</FormLabel>
                            <Input type="text" placeholder="Email" {...register("email", {
                                pattern: {
                                    value: /^\S+@\S+$/i, message: "Formato email non valido"
                                }
                            })} />
                            <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>


                    <GridItem>
                        <FormControl id={"dataNascita"} isInvalid={errors.dataNascita}>
                            <FormLabel>Data di Nascita</FormLabel>
                            <Input type="date" placeholder="2001-01-05" {...register("dataNascita", {
                                validate: value => {
                                    return isValidDate(value)
                                }
                            })} />
                            <FormErrorMessage>{errors.dataNascita && errors.dataNascita.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>

                    <GridItem>
                        <FormControl id={"telefono"} isInvalid={errors.telefono}></FormControl>
                        <FormLabel htmlFor="telefono">Telefono</FormLabel>
                        <Input type="text" {...register("telefono", {
                            maxLength: {
                                value: 10, message: "Il telefono è troppo lungo"
                            }, pattern: {value: /^[+03][0-9]{3,14}/i, message: "Formato cognome non valido"}
                        })} />
                        <FormErrorMessage>{errors.telefono && errors.telefono.message}</FormErrorMessage>
                    </GridItem>
                    <GridItem>
                        <FormControl id={"via"} isInvalid={errors.via}></FormControl>
                        <FormLabel htmlFor="via">Via</FormLabel>
                        <Input type="text" {...register("via", {
                            maxLength: {
                                value: 50, message: "Il via è troppo lungo"
                            }, pattern: {value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i, message: "Formato via non valido"}
                        })} />
                        <FormErrorMessage>{errors.via && errors.via.message}</FormErrorMessage>
                    </GridItem>

                    <GridItem>
                        <FormControl id={"cap"} isInvalid={errors.cap}></FormControl>
                        <FormLabel htmlFor="cap">Cap</FormLabel>
                        <Input type="text" {...register("cap", {
                            maxLength: {
                                value: 5, message: "Il cap è troppo lungo"
                            }, pattern: {value: /[0-9]{5}/i, message: "Formato cap non valido"}
                        })} />
                        <FormErrorMessage>{errors.cap && errors.cap.message}</FormErrorMessage>
                    </GridItem>

                    <GridItem>
                        <FormControl id={"citta"} isInvalid={errors.citta}></FormControl>
                        <FormLabel htmlFor="citta">Citta'</FormLabel>
                        <Input type="text" {...register("citta", {
                            maxLength: {
                                value: 20, message: "Il nome della citta' è troppo lungo"
                            }, pattern: {value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i, message: "Formato citta non valido"}
                        })} />
                        <FormErrorMessage>{errors.citta && errors.citta.message}</FormErrorMessage>
                    </GridItem>

                    <GridItem colSpan={2}>
                        <Button width={"full"} type={"submit"}>Modifica</Button>
                    </GridItem>

                </SimpleGrid>
            </form>
        </VStack>


    )


}