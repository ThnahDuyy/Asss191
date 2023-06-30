<?php

namespace App\Factory;

use App\Entity\Order;
use App\Entity\OrderItem;
use App\Entity\Items;

/**
 * Class OrderFactory.
 */
class OrderFactory
{
    /**
     * Creates an order.
     */
    public function create(): Order
    {
        $order = new Order();
        $order
            ->setStatus(Order::STATUS_CART)
            ->setCreatedAt(new \DateTime())
            ->setUpdatedAt(new \DateTime());
        return $order;
    }
    /**
     * Creates an item for a product.
     */
    public function createItem(Items $items): OrderItem
    {
        $item = new OrderItem();
        $item->setItem($items);
        $item->setQuantity(1);
        return $item;
    }
}
