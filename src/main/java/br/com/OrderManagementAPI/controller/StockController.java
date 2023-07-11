package br.com.OrderManagementAPI.controller;

import br.com.OrderManagementAPI.model.StockMovement;
import br.com.OrderManagementAPI.service.OrderAndStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@Api(tags = "Stock Management")
public class StockController {

    private final OrderAndStockService orderAndStockService;

    @GetMapping
    @ApiOperation("Get all stock movements")
    public ResponseEntity<List<StockMovement>> getAllStockMovements() {
        List<StockMovement> stockMovements = orderAndStockService.getAllStockMovements();
        return ResponseEntity.ok(stockMovements);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a stock movement by ID")
    public ResponseEntity<StockMovement> getStockMovementById(@PathVariable Long id) {
        StockMovement stockMovement = orderAndStockService.getStockMovementById(id);
        if (stockMovement != null) {
            return ResponseEntity.ok(stockMovement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation("Create a stock movement")
    public ResponseEntity<StockMovement> createStockMovement(@RequestBody StockMovement stockMovement) {
        StockMovement createdStockMovement = orderAndStockService.createStockMovement(stockMovement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStockMovement);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a stock movement")
    public ResponseEntity<StockMovement> updateStockMovement(@PathVariable Long id, @RequestBody StockMovement stockMovement) {
        StockMovement updatedStockMovement = orderAndStockService.updateStockMovement(id, stockMovement);
        if (updatedStockMovement != null) {
            return ResponseEntity.ok(updatedStockMovement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a stock movement")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
        boolean deleted = orderAndStockService.deleteStockMovement(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
