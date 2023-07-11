package br.com.OrderManagementAPI.service;

import br.com.OrderManagementAPI.model.Item;
import br.com.OrderManagementAPI.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        return itemOptional.orElse(null);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item updatedItem) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            Item existingItem = itemOptional.get();
            existingItem.setName(updatedItem.getName());
            return itemRepository.save(existingItem);
        } else {
            return null;
        }
    }

    public boolean deleteItem(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            itemRepository.delete(itemOptional.get());
            return true;
        } else {
            return false;
        }
    }

}
