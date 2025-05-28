package com.example.stockeasy.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class ProductoDaoTest {

    private lateinit var db: RoomDatabaseRealForTest
    private lateinit var productoDao: ProductoDao
    private lateinit var listaDao: ListaDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseRealForTest::class.java)
            .allowMainThreadQueries()
            .build()
        productoDao = db.productoDao()
        listaDao = db.listaDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertarYLeerProducto() = runBlocking {
        val listaId = listaDao.insertarLista(
            ListaEntity(nombre = "abarrotes", descripcion = "test")
        ).toInt()

        productoDao.insertarProducto(
            ProductoEntity(nombre = "arroz", cantidad = 15, imagenBase64 = "", listaId = listaId)
        )

        val producto = productoDao.buscarPorNombreEnLista("arroz", listaId)

        assertEquals("arroz", producto?.nombre)
        assertEquals(15, producto?.cantidad)
        assertEquals(listaId, producto?.listaId)


    }
}
